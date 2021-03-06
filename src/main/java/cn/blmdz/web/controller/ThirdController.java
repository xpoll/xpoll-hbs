package cn.blmdz.web.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import cn.blmdz.hbs.util.JsonMapper;
import cn.blmdz.web.enums.ThirdChannel;
import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.service.ThirdUserService;
import cn.blmdz.web.third.ThirdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/third")
public class ThirdController {
    
	@Autowired
	@Qualifier("alipayThirdManager")
	private ThirdManager alipayThirdManager;

	@Autowired
	@Qualifier("sinaThirdManager")
	private ThirdManager sinaThirdManager;

	@Autowired
	@Qualifier("baiduThirdManager")
	private ThirdManager baiduThirdManager;

	private ThirdManager thirdManager;
	
	@Autowired
	private ThirdUserService thirdUserService;

	/**
	 * 渠道切换
	 */
	private void channel(ThirdUser tuser) {
		switch (tuser.getThird()) {
		case ALIPAY:
			thirdManager = alipayThirdManager;
			break;
			
		case SINA:
			thirdManager = sinaThirdManager;
			break;
			
		case BAIDU:
			thirdManager = baiduThirdManager;
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * 第三方默认入口
	 */
	@RequestMapping
	public String defaults(HttpServletRequest request, HttpServletResponse response) {
		map(request);
		
		ThirdUser tuser = ThirdUtil.checkCode(request);
		if (tuser == null) {
			// 异常
		}
		third(request, response, tuser);
		return null;
	}

	/**
	 * 阿里入口
	 */
	@RequestMapping(value="/alipay")
	public String alipay(HttpServletRequest request, HttpServletResponse response) {
		map(request);
		ThirdUser tuser = new ThirdUser(ThirdChannel.ALIPAY);
		third(request, response, tuser);
		return null;
	}

	/**
	 * 新浪入口
	 */
	@RequestMapping(value="/sina")
	public String sina(HttpServletRequest request, HttpServletResponse response) {
		map(request);
		ThirdUser tuser = new ThirdUser(ThirdChannel.SINA);
		third(request, response, tuser);
		return null;
	}
	
	/**
	 * 百度入口
	 */
	@RequestMapping(value="/baidu")
	public String baidu(HttpServletRequest request, HttpServletResponse response) {
		map(request);
		ThirdUser tuser = new ThirdUser(ThirdChannel.BAIDU);
		third(request, response, tuser);
		return null;
	}

	/**
	 * 入口公共处理
	 */
	private void third(HttpServletRequest request, HttpServletResponse response, ThirdUser tuser) {
		channel(tuser);
		tuser = ThirdUtil.getThirdUserId(request, response, tuser, thirdManager, false);
		if (tuser == null) return ;
		
		thirdUserService.recording(tuser);
		log.info("用户信息: {}", tuser);
		
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印入参
	 */
	private void map(HttpServletRequest request) {
		Map<String, String> requestMap = Maps.newHashMap();
		Enumeration<String> enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			String param = enums.nextElement();
			requestMap.put(param, request.getParameter(param));
		}
		log.info("接收参数: {}", JsonMapper.nonDefaultMapper().toJson(requestMap));
	}


    /**
     * 领卡入口
     */
    @RequestMapping(value="/card")
    public void card(HttpServletRequest request, HttpServletResponse response) {
        map(request);
        channel(new ThirdUser(ThirdChannel.tran(request.getParameter("out_string"))));
        thirdManager.card(request.getParameter("request_id"), request.getParameter("template_id"), request.getParameter("auth_code"));
        try {
            response.sendRedirect(thirdManager.cardLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共测试
     */
    @RequestMapping(value="/test")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) {
        map(request);
    }
}
