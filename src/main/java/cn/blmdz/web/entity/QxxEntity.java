package cn.blmdz.web.entity;

import java.util.Date;

import cn.blmdz.web.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

public class QxxEntity {
	
	/**
	 * @see cn.blmdz.web.enums.StatusType
	 */
	@Getter
	@Setter
	private Integer status;
	@Getter
	@Setter
	private Long cid;
	@Getter
	@Setter
	private Long uid;
	@Getter
	@Setter
	private Date cdate;
	@Getter
	@Setter
	private Date udate;

	/**
	 * 默认创建
	 */
	public void create() {
		Date date = new Date();
		this.setStatus(StatusType.OK.value());
		this.setCid(0L);
		this.setUid(0L);
		this.setCdate(date);
		this.setUdate(date);
	}
	
	/**
	 * 默认更新
	 */
	public void update() {
		Date date = new Date();
		this.setStatus(StatusType.OK.value());
		this.setUid(0L);
		this.setUdate(date);
	}
}
