package cn.blmdz.third;

import java.net.URLDecoder;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayMarketingCardTemplateModifyModel;
import com.alipay.api.internal.util.AlipayLogger;
import com.alipay.api.response.AlipayMarketingCardActivateurlApplyResponse;

import cn.blmdz.web.third.AliPayConfig;
import cn.blmdz.web.third.AlipaySDK;

/**
 * <pre>
 * 0. 卡模板图标
 * 1. 获取应用授权
 * 2. 查询应用授权信息
 * 3. 创建会员卡模板
 * 4. 查询会员卡模板 
 * 5. 设置表单信息
 * 6. 获取会员卡领卡投放链接
 * 7. 更新会员卡模板-update
 * 8. 设置表单信息-update
 * 9. 获取会员卡领卡投放链接-update
 * </pre>
 * 
 * @author yongzongyang
 * @date 2017年8月8日
 */
public class ConfigAlipay {
	public final static String image_url = "C:\\bg.png";

//	public final static String url = "http://api-uat.maxxipoint.com";
//	public final static String url = "http://m-uat.maxxipoint.com";
	public final static String url = "http://blmdz.cn/third/card";
	// -------------------全家卡模板基本配置
	public final static String name = "熙小浅";
	public final static String color = "rgb(230,0,18)";
	public final static String prefix = "qxx";
	public final static String writeOffType = "barcode";

//	//------------------全家北京--有效期[2017-08-07 13:49:44 ~ 2018-08-07 13:49:44]----------------
//	public final static String app_id = "2016022501164379";
//	public final static String user_id = "2088611037009944";
//	public final static String auth_code = "915cf7a3b2fe450d95c148e6ca748X94";
//	public final static String auth_token = "201708BB2bbd7cd217e24aea89ea29c024e8dX94";
//	public final static String refresh_token = "201708BBa8cf14797be9423fa4ee0a6796e33X94";
	public final static String sign = "qxx_alipay";
	public final static String template_id = "20171113000000000578231000300753";
	public final static String logo_id = "XR-tC_4jSmO-hE_UrNhp-wAAACMAAQED";
	public final static String bg_id = "FfviK_x7S6yESp2zyAIZawAAACMAAQED";


	public static void main(String[] args) throws AlipayApiException {
	    AlipaySDK sdk = AliPayConfig.getInstance();
		AlipayLogger.setJDKDebugEnabled(true);
//		// 卡模板图标
//		sdk.upload(new File(image_url));
//		// 获取应用授权
//		sdk.appToken(auth_code);
//		// 查询应用授权信息
//		sdk.appInfo(auth_token);
//		// 刷新应用授权
//		sdk.refreshApp(refresh_token);
//		// 创建会员卡模板
//		AlipayMarketingCardTemplateCreateModel createModel = ModelFamily.alipayMarketingCardTemplateCreateModel(prefix, writeOffType, name, logo_id, color, bg_id, sign, url);
//		sdk.createTemplate(createModel);
		// 查询会员卡模板  
//		sdk.templateInfo(template_id);
//		// 更新会员卡模板
//		AlipayMarketingCardTemplateModifyModel modifyModel = ModelAlipay.alipayMarketingCardTemplateModifyModel(template_id, prefix, writeOffType, name, logo_id, color, bg_id, sign, "http://blmdz.xyz");
//		sdk.modifyTemplate(modifyModel);
//		// 设置表单信息
//		sdk.formTemplate(ModelFamily.alipayMarketingCardFormtemplateSetModel(template_id));
//		// 获取会员卡领卡投放链接 
		AlipayMarketingCardActivateurlApplyResponse res = sdk.cardLink(ModelAlipay.alipayMarketingCardActivateurlApplyModel(template_id, url));
		System.out.println(URLDecoder.decode(res.getApplyCardUrl()));
//		// 获取用户授权
//		sdk.userToken("064112dc041c457c8b4657b2a6f0PX75");
//		// 查询用户授权信息
//		sdk.userInfo("composeB7d548378d3a54eedbca992c518089C75");
//		// 查询会员表单信息
//		sdk.memberCardForm("20170829018940520502652207754", template_id, "composeBe959c6769403498bb069f9ce286e5X75", auth_token);
//		// 会员卡开卡
//        Calendar max = Calendar.getInstance();
//        AlipayMarketingCardOpenModel model = new AlipayMarketingCardOpenModel();
//        model.setOutSerialNo("20171113121212222000001");
//        model.setCardTemplateId(template_id);
//        
//        CardUserInfo cardUserInfo = new CardUserInfo();
//        cardUserInfo.setUserUniIdType("UID");// 默认
//        cardUserInfo.setUserUniId("2088702372638754");
//        model.setCardUserInfo(cardUserInfo);
//        MerchantCard cardExtInfo = new MerchantCard();
//        cardExtInfo.setOpenDate(max.getTime());
//        max.set(Calendar.YEAR, max.get(Calendar.YEAR) + 100);// 有效期默认100年
//        
//        cardExtInfo.setValidDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(max.getTime()));// 默认格式
//        cardExtInfo.setExternalCardNo("8888888888888");
//        model.setCardExtInfo(cardExtInfo);
//        
//        MerchantMenber memberExtInfo = new MerchantMenber();
//        memberExtInfo.setName("杨");
//        memberExtInfo.setGende("MALE");// 默认格式
//        memberExtInfo.setCell("18224524752");
//        model.setMemberExtInfo(memberExtInfo);
//		
//		sdk.memberCardOpen(model, "composeB7d548378d3a54eedbca992c518089C75");
//		// 会员卡查询
//		sdk.memberCardInfo("family0003366523", auth_token);
//		// 会员卡删除
//		sdk.memberCardDelete("family0003366523", auth_token);
	}
}
