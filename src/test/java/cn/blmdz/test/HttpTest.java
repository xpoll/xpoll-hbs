package cn.blmdz.test;

import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.web.third.BaiduConfig;
import cn.blmdz.web.third.SinaConfig;
import cn.blmdz.web.third.SinaUser;

public class HttpTest {

	public static void main(String[] args) {
		//https://api.weibo.com/oauth2/authorize?client_id=2385672810&redirect_uri=http://blmdz.cn&response_type=code
		// 新浪登陆授权
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", SinaConfig.SINA_APP_KEY);
		values.put("client_secret", SinaConfig.SINA_APP_SECRET);
		values.put("grant_type", "authorization_code");
		values.put("code", "a14a2f3133051e519126c2b3ea0c71be");
		values.put("redirect_uri", SinaConfig.SINA_REDIRECT_URL);
//		post(SinaConfig.SINA_USER_TOKEN_URL, values);
		
		//{"access_token":"2.00vwJMBDiTC9bCad68342e5bcpDkfD","remind_in":"157679999","expires_in":157679999,"uid":"2766073017"}
		// 获取用户信息
		values = Maps.newHashMap();
		values.put("uid", "2766073017");
		values.put("source", SinaConfig.SINA_APP_KEY);
		values.put("access_token", "2.00vwJMBDiTC9bCad68342e5bcpDkfD");
//		getSina(SinaConfig.SINA_USER_INFO, values);
		
		//http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=YQlsGnkZ6pGeeLmyv9FLoquF&redirect_uri=http://blmdz.cn&scope=basic&display=page
		// 百度登陆授权
		values = Maps.newHashMap();
		values.put("client_id", BaiduConfig.BAIDU_APP_KEY);
		values.put("client_secret", BaiduConfig.BAIDU_APP_SECRET);
		values.put("grant_type", "authorization_code");
		values.put("code", "94dfe68fb39cf19ca989e37fa9fff448");
		values.put("redirect_uri", BaiduConfig.BAIDU_REDIRECT_URL);
		post(BaiduConfig.BAIDU_USER_TOKEN_URL, values);
		
		//{"expires_in":2592000,"refresh_token":"22.0b5420597f4d94dbbdaf027deb3425ee.315360000.1814066600.388045557-9823512","access_token":"21.b526841d54b9e1d5f6649cfa29a35685.2592000.1501298600.388045557-9823512","session_secret":"0f638a9d5a77809db46179aee2046c41","session_key":"9mnRJqQKEqh9eAhjW6gtXQwS+ec11TaylyuCMvfG74cVT+a2YrAMhB+hjHALVdmlalpK2e61wppG59w8Y5v8OOtB7WLWNNch","scope":"basic"}
		// 获取用户信息
		values = Maps.newHashMap();
		values.put("access_token", "21.b526841d54b9e1d5f6649cfa29a35685.2592000.1501298600.388045557-9823512");
//		post("https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser", values);
		
		//{"uid":"388045557","uname":"blmdz521","portrait":"5417626c6d647a3532312a25"}
		//http://himg.bdimg.com/sys/portrait/item/5417626c6d647a3532312a25.jpg
		
		
		// qq登陆授权
//https://graph.qq.com/oauth/show?which=Login&display=pc&response_type=code&client_id=1106248538&state=test&redirect_uri=http://blmdz.cn
//
//https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=1106255490&redirect_uri=http://blmdz.cn&state=test&display=pc
//
//https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=1106255490&client_secret=d50OR449Hw8o1r8v&code=F264F9481B29C571B3AAEBE35E0A1A63&redirect_uri=http://blmdz.cn
//access_token=08A2BF2AF64A85CA6F0345ED0F62E19A&expires_in=7776000&refresh_token=3D3F274CD43E807E7912B3E482E64669
//
//https://graph.qq.com/oauth2.0/me?access_token=08A2BF2AF64A85CA6F0345ED0F62E19A
//callback( {"client_id":"1106255490","openid":"8607F3B5D7E7BDADAE03975EDBA4B25E"} ); 
//
//https://graph.qq.com/user/get_user_info?access_token=08A2BF2AF64A85CA6F0345ED0F62E19A&oauth_consumer_key=1106255490&openid=8607F3B5D7E7BDADAE03975EDBA4B25E
	}

	public static void getSina(String url, Map<String, String> values) {
		HttpRequest request = HttpRequest.get(url, values, false);
		System.out.println(request.ok());
		SinaUser user = JsonMapper.nonEmptyMapper().fromJson(request.body(), SinaUser.class);
		String prefix = "http://weibo.com/";
		user.setProfile_url(prefix + user.getProfile_url()); 
		System.out.println(user.toString());
	}
	public static void post(String url, Map<String, String> values) {
		HttpRequest request = HttpRequest.post(url);
		request.form(values);
		System.out.println(request.ok());
		System.out.println(request.body());
	}
}
