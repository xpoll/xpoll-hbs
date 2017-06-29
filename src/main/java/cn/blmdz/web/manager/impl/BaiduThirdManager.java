package cn.blmdz.web.manager.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.third.BaiduConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("baiduThirdManager")
public class BaiduThirdManager implements ThirdManager {

	@Override
	@SuppressWarnings("unchecked")
	public ThirdUser getThirdUserId(String authCode, ThirdUser tuser) {
		
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", BaiduConfig.BAIDU_APP_KEY);
		values.put("client_secret", BaiduConfig.BAIDU_APP_SECRET);
		values.put("grant_type", "authorization_code");
		values.put("code", authCode);
		values.put("redirect_uri", BaiduConfig.BAIDU_REDIRECT_URL);
		HttpRequest reqToken = HttpRequest.post(BaiduConfig.BAIDU_USER_TOKEN_URL);
		reqToken.form(values);
		if (reqToken.ok()) {
			values = Maps.newHashMap();
			values = JsonMapper.nonEmptyMapper().fromJson(reqToken.body(), Map.class);
			String accessToken = values.get("access_token");
			values = Maps.newHashMap();
			values.put("access_token", accessToken);
			HttpRequest reqInfo = HttpRequest.post(BaiduConfig.BAIDU_USER_INFO);
			reqInfo.form(values);
			if (reqInfo.ok()) {

				values = Maps.newHashMap();
				values = JsonMapper.nonEmptyMapper().fromJson(reqInfo.body(), Map.class);
				tuser.setNick(values.get("uname"));
				tuser.setAvatar(BaiduConfig.BAIDU_AVATAR_PREFIX + values.get("portrait") + BaiduConfig.BAIDU_AVATAR_SUFFIX);
				tuser.setThirdUserId(values.get("uid"));
				return tuser;
			}
			log.error("token: {}", reqInfo.body());
		}
		log.error("token: {}", reqToken.body());
		return null;
	}

}
