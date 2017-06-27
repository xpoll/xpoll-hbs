package cn.blmdz.web.other;

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
	private Long id;
	private String thirdUserId;
	private ThirdChannel third;
	
	private String nick;
	private String avatar;
}
