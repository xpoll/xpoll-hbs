package cn.blmdz.web.model;

import cn.blmdz.web.enums.ThirdChannel;
import lombok.Data;

/**
 * 第三方用户信息
 * 
 * @author yongzongyang
 * @date 2017年6月9日
 */
@Data
public class ThirdUser {
	/**
	 * 系统内主键ID
	 */
	private Long id;
	/**
	 * 第三方用户ID
	 */
	private String thirdUserId;
	/**
	 * 类型
	 */
	private ThirdChannel third;
	/**
	 * 昵称
	 */
	private String nick;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 地址
	 */
	private String url;
}
