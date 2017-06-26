package cn.blmdz.test;

import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

public class HttpTest {

	public static void main(String[] args) {
		HttpRequest request = HttpRequest.post("https://api.weibo.com/oauth2/access_token");
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", "2385672810");
		values.put("client_secret", "d0e3001da28ce22f9e09dfbcacb3d826");
		values.put("grant_type", "authorization_code");
		values.put("code", "1c46ca0a45b4c60d64736001e12678a7");
		values.put("redirect_uri", "http://blmdz.cn");
		request.form(values);
		System.out.println(request.created());
		System.out.println(request.body());
		
	}
}
