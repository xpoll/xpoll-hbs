package cn.blmdz.test;

import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.web.third.SinaUser;

public class HttpTest {

	public static void main(String[] args) {
		//https://api.weibo.com/oauth2/authorize?client_id=2385672810&redirect_uri=http://blmdz.cn&response_type=code
		// 登陆授权
		String url = "https://api.weibo.com/oauth2/access_token";
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", "2385672810");
		values.put("client_secret", "d0e3001da28ce22f9e09dfbcacb3d826");
		values.put("grant_type", "authorization_code");
		values.put("code", "a14a2f3133051e519126c2b3ea0c71be");
		values.put("redirect_uri", "http://blmdz.cn");
		post(url, values);
		//{"access_token":"2.00vwJMBDiTC9bCad68342e5bcpDkfD","remind_in":"157679999","expires_in":157679999,"uid":"2766073017"}
		//https://api.weibo.com/2/users/show.json?uid=2766073017&source=2385672810&access_token=2.00vwJMBDiTC9bCad68342e5bcpDkfD
		url = "https://api.weibo.com/2/users/show.json";
		values = Maps.newHashMap();
		values.put("uid", "2766073017");
		values.put("source", "2385672810");
		values.put("access_token", "2.00vwJMBDiTC9bCad68342e5bcpDkfD");
		get(url, values);
	}

	private static void get(String url, Map<String, String> values) {
		HttpRequest request = HttpRequest.get(url, values, false);
		System.out.println(request.ok());
		SinaUser user = JsonMapper.nonEmptyMapper().fromJson(request.body(), SinaUser.class);
		String prefix = "http://weibo.com/";
		user.setProfile_url(prefix + user.getProfile_url()); 
		System.out.println(user.toString());
	}
	private static void post(String url, Map<String, String> values) {
		HttpRequest request = HttpRequest.post(url);
		request.form(values);
		System.out.println(request.ok());
		System.out.println(request.body());
	}
}
