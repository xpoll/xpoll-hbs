package cn.blmdz.hbs.hbs;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.config.HbsProperties;

@Component
public class RenderHelpers extends AbstractHelpers {

	@Autowired
	private HandlebarsEngine handlebarsEngine;
	@Autowired
	private HbsProperties properties;

	protected void fillHelpers(Map<String, Helper<?>> helpers) {
		helpers.put("inject", new Helper<String>() {
			@SuppressWarnings("unchecked")
			public CharSequence apply(String compPath, Options options) throws IOException {
				Map<String, Object> tempContext = Maps.newHashMap();
				if (options.context.model() instanceof Map) {
					tempContext.putAll((Map<String, Object>) options.context.model());
					tempContext.putAll(options.hash);
				}
				return new SafeString(handlebarsEngine.execComponent(compPath, tempContext));
			}
		});
		helpers.put("component", new Helper<String>() {
			@Override
			public CharSequence apply(String tag, Options options) throws IOException {
				
				String path = "/" + properties.getHome() + "/" + options.fn.filename().replace(":", "s/");
				
				StringBuilder sb = new StringBuilder();
				
				if (tag.contains("js")) {
					sb.append("<script type=\"text/javascript\" src=\"")
					.append(path)
					.append(".js")
					.append("\"></script>");
				}
				
				if (tag.contains("css")) {
					sb.append("<link rel=\"stylesheet\" href=\"")
					.append(path)
					.append(".css")
					.append("\">");
				}
				return new SafeString(options.fn() + sb.toString());
			}
		});
	}
}
