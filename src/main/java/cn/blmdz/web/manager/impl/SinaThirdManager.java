package cn.blmdz.web.manager.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.third.SinaConfig;
import cn.blmdz.web.third.SinaUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("sinaThirdManager")
public class SinaThirdManager implements ThirdManager {

	@Override
	@SuppressWarnings("unchecked")
	public ThirdUser getThirdUserId(String authCode, ThirdUser tuser) {
		Map<String, String> values = Maps.newHashMap();
		values.put("client_id", SinaConfig.SINA_APP_KEY);
		values.put("client_secret", SinaConfig.SINA_APP_SECRET);
		values.put("grant_type", "authorization_code");
		values.put("code", authCode);
		values.put("redirect_uri", SinaConfig.SINA_REDIRECT_URL);

		HttpRequest reqToken = HttpRequest.post(SinaConfig.SINA_USER_TOKEN_URL);
		reqToken.form(values);
		if (reqToken.ok()) {
			
			values = Maps.newHashMap();
			values = JsonMapper.nonEmptyMapper().fromJson(reqToken.body(), Map.class);
			String access_token = values.get("access_token");
			String uid = values.get("uid");

			values = Maps.newHashMap();
			values.put("uid", uid);
			values.put("source", SinaConfig.SINA_APP_KEY);
			values.put("access_token", access_token);

			HttpRequest reqInfo = HttpRequest.get(SinaConfig.SINA_USER_INFO, values, false);
			if (reqInfo.ok()) {
				SinaUser suser = JsonMapper.nonEmptyMapper().fromJson(reqInfo.body(), SinaUser.class);
				suser.setProfile_url(SinaConfig.SINA_PREFIX + suser.getProfile_url()); 
				tuser.setThirdUserId(String.valueOf(suser.getId()));
				tuser.setNick(suser.getScreen_name());
				tuser.setAvatar(suser.getAvatar_large());
				tuser.setUrl(suser.getProfile_url());
				return tuser;
			}
			log.error("token: {}", reqInfo.body());
		}
		log.error("token: {}", reqToken.body());
		return null;
	}

}
