package cn.blmdz.web.other;

/**
 * 第三方渠道
 * 
 * @author yongzongyang
 * @date 2017年6月9日
 */
public enum ThirdChannel {

	ALIPAY("alipay", "支付宝"),
	WECHAT("wechat", "微信"),
	QQ("qq", "QQ");
	
	String code;
	String desc;
	
	private ThirdChannel(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public String code() {
		return  this.code;
	}

	public static ThirdChannel tran(String code) {
		for (ThirdChannel item : ThirdChannel.values()) {
			if (item.code.equals(code)) return item;
		}
		return null;
	}
}
