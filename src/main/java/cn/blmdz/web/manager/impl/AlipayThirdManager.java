package cn.blmdz.web.manager.impl;

import org.springframework.stereotype.Component;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.third.AliPayConfig;
import cn.blmdz.web.third.AlipaySDK;

@Component("alipayThirdManager")
public class AlipayThirdManager implements ThirdManager {
	
	private AlipaySDK alipaySDK = AliPayConfig.getInstance();
	
	@Override
	public ThirdUser getThirdUserId(String authCode, ThirdUser tuser) {
		AlipaySystemOauthTokenResponse respToken = alipaySDK.userToken(authCode);
		if (respToken.isSuccess()) {
			AlipayUserInfoShareResponse respInfo = alipaySDK.userInfo(respToken.getAccessToken());
			if (respInfo.isSuccess()) {
				tuser.setNick(respInfo.getNickName());
				tuser.setThirdUserId(respInfo.getUserId());
				tuser.setAvatar(respInfo.getAvatar());
				return tuser;
			}
		}
		return null;
	}
}
