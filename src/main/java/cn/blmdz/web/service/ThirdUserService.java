package cn.blmdz.web.service;

import cn.blmdz.web.model.ThirdUser;

/**
 * 第三方用户信息
 * @author lm
 */
public interface ThirdUserService {

	/**
	 * 记录第三方用户
	 * @param tuser
	 */
	void recording(ThirdUser tuser);
}
