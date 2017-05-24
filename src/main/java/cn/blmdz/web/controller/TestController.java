package cn.blmdz.web.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

@RestController
@RequestMapping("/nexus")
public class TestController {

	@RequestMapping
	public Map<String, String> nexus(HttpServletRequest request) {
		Map<String, String> maps = Maps.newHashMap();
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String param = e.nextElement();
			maps.put(param, request.getParameter(param));
		}
		return maps;
	}
}
