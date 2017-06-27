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
		AlipaySystemOauthTokenResponse responseToken = alipaySDK.userToken(authCode);
		AlipayUserInfoShareResponse responseInfo = alipaySDK.userInfo(responseToken.getAccessToken());
		tuser.setNick(responseInfo.getNickName());
		tuser.setThirdUserId(responseInfo.getUserId());
		tuser.setAvatar(responseInfo.getAvatar());
		return tuser;
	}
}
