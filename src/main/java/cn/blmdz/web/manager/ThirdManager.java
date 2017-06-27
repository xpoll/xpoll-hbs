package cn.blmdz.web.manager;

import cn.blmdz.web.model.ThirdUser;

/**
 * 外接第三方必须实现此接口
 * 
 * @author lm
 */
public interface ThirdManager {

	/**
	 * 获取用户基本信息（thirdUserId，nick，avatar）
	 * 
	 * @param authCode
	 * @param tuser
	 * @return
	 */
	ThirdUser getThirdUserId(String authCode, ThirdUser tuser);
}
