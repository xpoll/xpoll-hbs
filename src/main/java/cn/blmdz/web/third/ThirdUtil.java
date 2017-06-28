package cn.blmdz.web.third;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.blmdz.web.enums.ThirdChannel;
import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.ThirdUser;

/**
 * @author yongzongyang
 * @date 2017年6月13日
 */
public class ThirdUtil {

	// 公共参数
	public static final String THIRD = "third";
	
	/**
	 * 获取第三方用户ID<br>
	 * 
	 * 调用前请校验信息<br>
	 * 调用后判断是否为空return进行sendRedirect<br>
	 * @param request
	 * @param response
	 * @param user 必要基础信息（third、business、type）
	 * @param thirdPartyManager 设置UserId和AccessToken
	 * @param session true 走session取，false 不走session取但设置session
	 * @return
	 */
	public static ThirdUser getThirdUserId(HttpServletRequest request, HttpServletResponse response, ThirdUser tuser, ThirdManager thirdManager, boolean session) {
//		if (session) {
//			if (request.getSession().getAttribute(SDKUtil.THIRD_USER_ID) != null) {
//				return (ThirdUser) request.getSession().getAttribute(SDKUtil.THIRD_USER_ID);
//			}
//		}
		String authCode = null;
		switch (tuser.getThird()) {
		case ALIPAY:
			authCode = request.getParameter(AliPayConfig.ALIPAY_AUTH_CODE);
			break;
			
		case SINA:
			authCode = request.getParameter(SinaConfig.SINA_AUTH_CODE);
			break;
			
		default:
			break;
		}
		if (StringUtils.isBlank(authCode)) {
			try {
				response.sendRedirect(ThirdUtil.getAuthCode(request.getRequestURL().toString(), tuser));
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * thirdPartyManager设置UserId
		 */
		return thirdManager.getThirdUserId(authCode, tuser);
	}
	
	/**
	 * 校验必要信息
	 * @param request
	 * @return
	 */
	public static ThirdUser checkCode(HttpServletRequest request) {
		
		ThirdChannel thirdChannel = ThirdChannel.tran(request.getParameter(THIRD));
		
		return (thirdChannel == null) ? null : new ThirdUser(thirdChannel);
	}
	
	/**
	 * 拼接地址
	 * @param url
	 * @param user
	 * @return
	 */
	private static String getAuthCode(String url, ThirdUser user) {
		StringBuilder sb = new StringBuilder();
		switch (user.getThird()) {
		case ALIPAY:
			sb.append(AliPayConfig.ALIPAY_USER_AUTH_URL)
			.append("?")
			.append("app_id=")
			.append(AliPayConfig.ALIPAY_APP_ID)
			.append("&scope=auth_base,auth_user");
			break;
			
		case SINA:
			sb.append(SinaConfig.SINA_USER_AUTH_URL)
			.append("?")
			.append("client_id=")
			.append(SinaConfig.SINA_APP_KEY)
			.append("&response_type=code");
			break;
			
		default:
			break;
		}
		sb.append("&")
		.append(THIRD)
		.append("=")
		.append(user.getThird().code())
		.append("&redirect_uri=")
		.append(url);
		return sb.toString();
	}
}
