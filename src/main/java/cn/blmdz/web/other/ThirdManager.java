package cn.blmdz.web.other;

public interface ThirdManager {

	/**
	 * @param auth_code
	 * @param tuser
	 * @return
	 */
	ThirdUser getThirdUserId(String auth_code, ThirdUser tuser);
}
