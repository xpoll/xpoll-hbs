package cn.blmdz.web.other;

import lombok.Data;

/**
 * 第三方用户信息
 * 
 * @author yongzongyang
 * @date 2017年6月9日
 */
@Data
public class ThirdUser {
	private Long id;
	private String userId;
	private ThirdChannel third;
	
	private String nickName;
	private String avatar;
}
