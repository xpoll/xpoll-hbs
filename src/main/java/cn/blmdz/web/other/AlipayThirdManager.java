package cn.blmdz.web.other;

import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

@Service("alipayThirdManager")
public class AlipayThirdManager implements ThirdManager {
	
	private AlipaySDK alipaySDK = AliPayConfig.getInstance();
	
	@Override
	public ThirdUser getThirdUserId(String auth_code, ThirdUser tuser) {
		AlipaySystemOauthTokenResponse responseToken = alipaySDK.userToken(auth_code);
		AlipayUserInfoShareResponse responseInfo = alipaySDK.userInfo(responseToken.getAccessToken());
		tuser.setNick(responseInfo.getNickName());
		tuser.setThirdUserId(responseInfo.getUserId());
		tuser.setAvatar(responseInfo.getAvatar());
		return tuser;
	}
}
