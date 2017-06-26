package cn.blmdz.test;

import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

public class HttpTest {

	public static void main(String[] args) {
		//https://api.weibo.com/oauth2/authorize?client_id=2385672810&redirect_uri=http://blmdz.cn&response_type=code
		HttpRequest request = HttpRequest.post("https://api.weibo.com/oauth2/access_token");
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", "2385672810");
		values.put("client_secret", "d0e3001da28ce22f9e09dfbcacb3d826");
		values.put("grant_type", "authorization_code");
		values.put("code", "a14a2f3133051e519126c2b3ea0c71be");
		values.put("redirect_uri", "http://blmdz.cn");
		request.form(values);
		System.out.println(request.ok());
		System.out.println(request.body());
		//{"access_token":"2.00vwJMBDiTC9bCad68342e5bcpDkfD","remind_in":"157679999","expires_in":157679999,"uid":"2766073017"}
		
	}
}
