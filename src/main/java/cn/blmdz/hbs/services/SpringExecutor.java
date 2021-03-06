package cn.blmdz.hbs.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.blmdz.hbs.util.Export;
import cn.blmdz.hbs.util.ParamUtil;
import cn.blmdz.hbs.util.ParamUtil.MethodInfo;
import cn.blmdz.hbs.util.ParamUtil.ParamInfo;
import cn.blmdz.hbs.util.Splitters;

@Component
public class SpringExecutor {
	private static final Logger log = LoggerFactory.getLogger(SpringExecutor.class);

	private final LoadingCache<String, MethodInfo> methodInfos;
	@Autowired
	private ApplicationContext applicationContext;

	public SpringExecutor() {
		CacheLoader<String, MethodInfo> loader = new CacheLoader<String, MethodInfo>() {
			@Override
			public MethodInfo load(String key) throws Exception {
				List<String> parts = Splitters.COLON.splitToList(key);
				if (parts.size() != 2)
					throw new IllegalArgumentException(
							"bad api format,should be interfaceName:methodName,but is: " + key);

				Class<?> klass = Class.forName(parts.get(0));
				Object bean = applicationContext.getBean(klass);
				Method method = findMethodByName(klass, parts.get(1));
				if (method == null)
					throw new NoSuchMethodException("failed to find method: " + key);

				return ParamUtil.getMethodInfo(bean, method);
			}
		};
		methodInfos = CacheBuilder.newBuilder().build(loader);
	}

	public boolean detectType(String uri) {
		try {
			MethodInfo result = methodInfos.getUnchecked(uri);
			return result != null;
		} catch (Exception e) {
			log.warn(
					"detect spring service type for [{}] failed, it\'s maybe not an exception that need to attention. {}",
					uri, e.getMessage());
			log.debug("detect spring service type for [{}] failed, debug info: {}", uri,
					Throwables.getStackTraceAsString(e));
			return false;
		}
	}

	public Object exec(String uri, Map<String, Object> params) {
		if (Strings.isNullOrEmpty(uri)) {
			return null;
		} else {
			MethodInfo methodInfo = (MethodInfo) this.methodInfos.getUnchecked(uri);
			LinkedHashMap<String, ParamInfo> paramsInfo = methodInfo.getParams();
			Object[] concernedParams = new Object[paramsInfo.size()];
			int index = 0;

			for (String paramName : paramsInfo.keySet()) {
				ParamInfo paramInfo = (ParamInfo) paramsInfo.get(paramName);
				Object param = ParamConverter.convertParam(paramName, paramInfo.getClazz(), params,
						paramInfo.isOptional());
				concernedParams[index++] = ParamUtil.convert(param, paramInfo, params);
			}

			try {
				return methodInfo.getMethod().invoke(methodInfo.getBean(), concernedParams);
			} catch (IllegalAccessException e) {
				log.error("illegal access method, service: {}", uri, e);
			} catch (InvocationTargetException e) {
				log.error("invocation target exception, service: {}", uri, e);
			}
			return null;
		}

	}

	private Method findMethodByName(Class<?> beanClazz, String methodName) {
		Method[] methods = beanClazz.getMethods();

		for (Method method : methods) {
			if (method.getName().equals(methodName) && method.getAnnotation(Export.class) != null)
				return method;
		}
		return null;
	}
}