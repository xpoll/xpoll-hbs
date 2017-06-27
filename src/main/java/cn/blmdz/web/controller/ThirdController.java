package cn.blmdz.web.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

import cn.blmdz.web.dao.ThirdUserDao;
import cn.blmdz.web.entity.QxxThirdUser;
import cn.blmdz.web.other.ThirdManager;
import cn.blmdz.web.other.ThirdUser;
import cn.blmdz.web.other.ThirdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ThirdController {

	@Autowired
	@Qualifier("alipayThirdManager")
	private ThirdManager alipayThirdManager;

	private ThirdManager thirdManager;
	
	@Autowired
	private ThirdUserDao thirdUserDao;

	/**
	 * 渠道切换
	 */
	private void channel(ThirdUser tuser) {
		switch (tuser.getThird()) {
		case ALIPAY:
			thirdManager = alipayThirdManager;
			break;
		default:
			break;
		}
	}
	
	@RequestMapping(value="/third")
	public String third(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> requestMap = Maps.newHashMap();
		Enumeration<String> enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			String param = enums.nextElement();
			requestMap.put(param, request.getParameter(param));
		}
		log.info("接收参数: {}", requestMap);
		
		ThirdUser tuser = ThirdUtil.checkCode(request);
		if (tuser == null) {
			// 异常
		}
		channel(tuser);
		tuser = ThirdUtil.getThirdUserId(request, response, tuser, thirdManager, false);
		if (tuser == null) return null;
		log.info("用户信息: {}", tuser);
		
		QxxThirdUser qtuser = thirdUserDao.findByThirdUserId(tuser.getThird(), tuser.getThirdUserId());
		if (qtuser == null) {
			log.info("插入");
			qtuser = new QxxThirdUser();
			BeanUtils.copyProperties(tuser, qtuser);
			thirdUserDao.create(qtuser);
		}
		tuser.setId(qtuser.getId());
		
		log.info("后用户信息: {}", tuser);
		
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
