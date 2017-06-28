package cn.blmdz.web.entity;

import java.io.Serializable;
import java.util.Date;

import cn.blmdz.web.enums.ThirdChannel;
import lombok.Data;

/**
 * 第三方登陆用户
 * @author lm
 */
@Data
public class QxxThirdUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 昵称
	 */
	private String nick;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 账号类型
	 */
	private ThirdChannel third;
	
	/**
	 * 第三方用户ID
	 */
	private String thirdUserId;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 用户地址
	 */
	private String url;

	/**
	 * @see cn.blmdz.web.enums.StatusType
	 */
	private Integer status;
	private Long cid;
	private Long uid;
	private Date cdate;
	private Date udate;
}