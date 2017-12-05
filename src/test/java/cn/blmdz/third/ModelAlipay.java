package cn.blmdz.third;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.api.domain.AlipayMarketingCardActivateurlApplyModel;
import com.alipay.api.domain.AlipayMarketingCardTemplateCreateModel;
import com.alipay.api.domain.AlipayMarketingCardTemplateModifyModel;
import com.alipay.api.domain.BudgetInfo;
import com.alipay.api.domain.ClauseTerm;
import com.alipay.api.domain.ConstraintInfo;
import com.alipay.api.domain.DisplayConfig;
import com.alipay.api.domain.KoubeiMarketingCampaignActivityCreateModel;
import com.alipay.api.domain.MoreInfoDTO;
import com.alipay.api.domain.PromoTool;
import com.alipay.api.domain.PublishChannel;
import com.alipay.api.domain.SendRule;
import com.alipay.api.domain.TemplateBenefitInfoDTO;
import com.alipay.api.domain.TemplateCardLevelConfDTO;
import com.alipay.api.domain.TemplateColumnInfoDTO;
import com.alipay.api.domain.TemplateFieldRuleDTO;
import com.alipay.api.domain.TemplateStyleInfoDTO;
import com.alipay.api.domain.UseRule;
import com.alipay.api.domain.Voucher;
import com.alipay.api.domain.VoucherDescDetail;

import cn.blmdz.hbs.util.JsonMapper;
/**
 * 全家model封装
 * @author yongzongyang
 * @date 2017年9月17日
 */
public class ModelAlipay {
	
	/**
	 * 会员卡模板创建 TODO
	 * 
	 * @param prefix 卡前缀
	 * @param writeOffType 卡类型
	 * @param name 卡名字
	 * @param logo_id logo图ID
	 * @param color 背景色
	 * @param bg_id 背景图ID
	 * @param sign 标志
	 * @param url 服务器域名
	 */
	public static AlipayMarketingCardTemplateCreateModel alipayMarketingCardTemplateCreateModel(
			String prefix,
			String writeOffType,
			String name,
			String logo_id,
			String color,
			String bg_id,
			String sign,
			String url
			) {
		AlipayMarketingCardTemplateCreateModel model = new AlipayMarketingCardTemplateCreateModel();
		model.setRequestId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		model.setCardType("OUT_MEMBER_CARD");
		model.setBizNoPrefix(prefix);
		model.setBizNoSuffixLen("10");
		model.setWriteOffType(writeOffType);
		
		//模板样式信息--必须
		TemplateStyleInfoDTO templateStyleInfo = new TemplateStyleInfoDTO();
		templateStyleInfo.setCardShowName(name);
		templateStyleInfo.setLogoId(logo_id); 
		templateStyleInfo.setColor(color);
		templateStyleInfo.setBackgroundId(bg_id); 
		templateStyleInfo.setBgColor(color);
		templateStyleInfo.setSlogan("会员权益享不停");
		templateStyleInfo.setSloganImgId("1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC");
		templateStyleInfo.setBrandName("魔寒居");
		model.setTemplateStyleInfo(templateStyleInfo);
		
		
		List<TemplateBenefitInfoDTO> benefitInfoList = new ArrayList<>();
		TemplateBenefitInfoDTO templateBenefitInfoDTO = new TemplateBenefitInfoDTO();
		templateBenefitInfoDTO.setTitle("优惠啊");
		List<String> benefitDesc = new ArrayList<>();
        benefitDesc.add("优惠啊1");
        benefitDesc.add("优惠啊优惠啊2");
        benefitDesc.add("优惠啊优惠啊优惠啊3");
		templateBenefitInfoDTO.setBenefitDesc(benefitDesc);
		Calendar max = Calendar.getInstance();
		templateBenefitInfoDTO.setStartDate(max.getTime());
		max.set(Calendar.YEAR, max.get(Calendar.YEAR) + 1);
		templateBenefitInfoDTO.setEndDate(max.getTime());
		model.setTemplateBenefitInfo(benefitInfoList);

		
		//栏位信息--必须
		List<TemplateColumnInfoDTO> columnInfoList = new ArrayList<>();

        TemplateColumnInfoDTO templateColumnInfoDTO = null;
        MoreInfoDTO moreInfo = null;

        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("QXX_HOME");
        moreInfo = new MoreInfoDTO();
//      moreInfo.setParams("{\"third\":\"alipay\"}");
        moreInfo.setTitle("千酌一梦醉独殇");
        moreInfo.setUrl(url);
        templateColumnInfoDTO.setMoreInfo(moreInfo);
        templateColumnInfoDTO.setTitle("首页");
        templateColumnInfoDTO.setOperateType("openWeb");
        templateColumnInfoDTO.setValue("进入首页");
        columnInfoList.add(templateColumnInfoDTO);
		
		model.setColumnInfoList(columnInfoList);
		
		//字段规则列表--必须
		List<TemplateFieldRuleDTO> fieldRuleList = new ArrayList<>();
		TemplateFieldRuleDTO templateFieldRuleDTO = new TemplateFieldRuleDTO();
		templateFieldRuleDTO.setFieldName("ValidDate");
		templateFieldRuleDTO.setRuleName("ASSIGN_FROM_REQUEST");
		templateFieldRuleDTO.setRuleValue("ValidDate");
		fieldRuleList.add(templateFieldRuleDTO);
		model.setFieldRuleList(fieldRuleList);

		return model;
	}
	

	/**
	 * 会员卡模板更新 TODO
	 * 
	 * @param templateId 模板ID
	 * @param prefix 卡前缀
	 * @param writeOffType 卡类型
	 * @param name 卡名字
	 * @param logo_id logo图ID
	 * @param color 背景色
	 * @param bg_id 背景图ID
	 * @param sign 标志
	 * @param url 服务器域名
	 * @param shops 店铺号，逗号隔开
	 * @param shop 店铺会员卡展示为true
	 */
	public static AlipayMarketingCardTemplateModifyModel alipayMarketingCardTemplateModifyModel(
			String templateId,
			String prefix,
			String writeOffType,
			String name,
			String logo_id,
			String color,
			String bg_id,
			String sign,
			String url
			) {
		AlipayMarketingCardTemplateModifyModel model = new AlipayMarketingCardTemplateModifyModel();
		model.setRequestId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		model.setBizNoPrefix(prefix);
		model.setWriteOffType(writeOffType);
		model.setTemplateId(templateId);
		
		//模板样式信息--必须
		TemplateStyleInfoDTO templateStyleInfo = new TemplateStyleInfoDTO();
		templateStyleInfo.setCardShowName(name);
		templateStyleInfo.setLogoId(logo_id); 
		templateStyleInfo.setColor(color);
		templateStyleInfo.setBackgroundId(bg_id); 
		templateStyleInfo.setBgColor(color);
        templateStyleInfo.setSlogan("会员权益享不停");
        templateStyleInfo.setSloganImgId("1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC");
        templateStyleInfo.setBrandName("魔寒居");
        model.setTemplateStyleInfo(templateStyleInfo);
        
        
        List<TemplateBenefitInfoDTO> benefitInfoList = new ArrayList<>();
        TemplateBenefitInfoDTO templateBenefitInfoDTO = new TemplateBenefitInfoDTO();
        templateBenefitInfoDTO.setTitle("优惠啊");
        List<String> benefitDesc = new ArrayList<>();
        benefitDesc.add("优惠啊1");
        benefitDesc.add("优惠啊优惠啊2");
        benefitDesc.add("优惠啊优惠啊优惠啊3");
        templateBenefitInfoDTO.setBenefitDesc(benefitDesc);
        Calendar max = Calendar.getInstance();
        templateBenefitInfoDTO.setStartDate(max.getTime());
        max.set(Calendar.YEAR, max.get(Calendar.YEAR) + 1);
        templateBenefitInfoDTO.setEndDate(max.getTime());
        benefitInfoList.add(templateBenefitInfoDTO);
        model.setTemplateBenefitInfo(benefitInfoList);
		
		//栏位信息--必须
		List<TemplateColumnInfoDTO> columnInfoList = new ArrayList<>();
		TemplateColumnInfoDTO templateColumnInfoDTO = null;
		MoreInfoDTO moreInfo = null;

        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("QXX_HOME");
        moreInfo = new MoreInfoDTO();
//      moreInfo.setParams("{\"third\":\"alipay\"}");
        moreInfo.setTitle("首页");
        moreInfo.setUrl(url);
        templateColumnInfoDTO.setMoreInfo(moreInfo);
        templateColumnInfoDTO.setTitle("首页");
        templateColumnInfoDTO.setOperateType("openWeb");
        templateColumnInfoDTO.setValue("进入首页");
        columnInfoList.add(templateColumnInfoDTO);
        
        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("QXX_MORE");
        moreInfo = new MoreInfoDTO();
        moreInfo.setTitle("叮咚");
        List<String> descs = new ArrayList<>();
        descs.add("叮咚。。");
        descs.add("叮咚叮咚。。");
        descs.add("叮咚叮咚叮咚。。");
        descs.add("叮咚叮咚叮咚叮咚。。");
        moreInfo.setDescs(descs);
        templateColumnInfoDTO.setMoreInfo(moreInfo);
        templateColumnInfoDTO.setTitle("千酌一梦醉独殇");
        templateColumnInfoDTO.setOperateType("openNative");
        templateColumnInfoDTO.setValue("更多");
        columnInfoList.add(templateColumnInfoDTO);
        
        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("QXX_AUTHOR");
        templateColumnInfoDTO.setTitle("作者：");
        templateColumnInfoDTO.setOperateType("staticinfo");
        templateColumnInfoDTO.setValue("木逸萧");
        columnInfoList.add(templateColumnInfoDTO);
        
        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("BALANCE");
        templateColumnInfoDTO.setTitle("余额");
        templateColumnInfoDTO.setOperateType("staticinfo");
        templateColumnInfoDTO.setValue("");
        columnInfoList.add(templateColumnInfoDTO);
        
        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("POINT");
        templateColumnInfoDTO.setTitle("积分");
        templateColumnInfoDTO.setOperateType("staticinfo");
        templateColumnInfoDTO.setValue("");
        columnInfoList.add(templateColumnInfoDTO);
        
        templateColumnInfoDTO = new TemplateColumnInfoDTO();
        templateColumnInfoDTO.setCode("LEVEL");
        templateColumnInfoDTO.setTitle("等级");
        templateColumnInfoDTO.setOperateType("staticinfo");
        templateColumnInfoDTO.setValue("");
        columnInfoList.add(templateColumnInfoDTO);
        
        


		model.setColumnInfoList(columnInfoList);
		
		//字段规则列表--必须
		List<TemplateFieldRuleDTO> fieldRuleList = new ArrayList<>();
		TemplateFieldRuleDTO templateFieldRuleDTO = new TemplateFieldRuleDTO();
		templateFieldRuleDTO.setFieldName("ValidDate");
		templateFieldRuleDTO.setRuleName("ASSIGN_FROM_REQUEST");
		templateFieldRuleDTO.setRuleValue("ValidDate");
		fieldRuleList.add(templateFieldRuleDTO);
        
        templateFieldRuleDTO = new TemplateFieldRuleDTO();
        templateFieldRuleDTO.setFieldName("Balance");
        templateFieldRuleDTO.setRuleName("ASSIGN_FROM_REQUEST");
        templateFieldRuleDTO.setRuleValue("Balance");
        fieldRuleList.add(templateFieldRuleDTO);

        templateFieldRuleDTO = new TemplateFieldRuleDTO();
        templateFieldRuleDTO.setFieldName("Point");
        templateFieldRuleDTO.setRuleName("ASSIGN_FROM_REQUEST");
        templateFieldRuleDTO.setRuleValue("Point");
        fieldRuleList.add(templateFieldRuleDTO);
        
        templateFieldRuleDTO = new TemplateFieldRuleDTO();
        templateFieldRuleDTO.setFieldName("Level");
        templateFieldRuleDTO.setRuleName("ASSIGN_FROM_REQUEST");
        templateFieldRuleDTO.setRuleValue("Level");
        fieldRuleList.add(templateFieldRuleDTO);
        
		model.setFieldRuleList(fieldRuleList);
		
		List<TemplateCardLevelConfDTO> cardLevelConf = new ArrayList<>();
		TemplateCardLevelConfDTO templateCardLevelConfDTO = new TemplateCardLevelConfDTO();
        templateCardLevelConfDTO.setLevel("VIP");
        templateCardLevelConfDTO.setLevelShowName("高级会员");
        templateCardLevelConfDTO.setLevelIcon(logo_id);
        templateCardLevelConfDTO.setLevelDesc("高级会员高级会员高级会员");
        cardLevelConf.add(templateCardLevelConfDTO);
        templateCardLevelConfDTO = new TemplateCardLevelConfDTO();
        templateCardLevelConfDTO.setLevel("NORMAL");
        templateCardLevelConfDTO.setLevelShowName("普通会员");
        templateCardLevelConfDTO.setLevelIcon(logo_id);
        templateCardLevelConfDTO.setLevelDesc("普通会员普通会员普通会员");
        cardLevelConf.add(templateCardLevelConfDTO);
		model.setCardLevelConf(cardLevelConf);
		
		return model;
	}
	
	/**
	 * 会员表单 TODO
	 */
	public static String alipayMarketingCardFormtemplateSetModel(String templateId) {

		List<String> list = new ArrayList<>();
		list.add("OPEN_FORM_FIELD_MOBILE");// 手机号
		list.add("OPEN_FORM_FIELD_GENDER");// 性别
		list.add("OPEN_FORM_FIELD_NAME");// 姓名
		list.add("OPEN_FORM_FIELD_BIRTHDAY_WITH_YEAR");// 生日
//		list.add("OPEN_FORM_FIELD_EMAIL");// 邮箱
//		list.add("OPEN_FORM_FIELD_BIRTHDAY");// 生日
//		list.add("OPEN_FORM_FIELD_IDCARD");// 身份证
//		list.add("OPEN_FORM_FIELD_ADDRESS");// 地址
//		list.add("OPEN_FORM_FIELD_CITY");// 城市
//		list.add("OPEN_FORM_FIELD_IS_STUDENT");// 是否学生认证
//		list.add("OPEN_FORM_FIELD_MEMBER_GRADE");// 会员等级

		Map<String, Object> required = new HashMap<>();
		required.put("common_fields", list);
		
		Map<String, Object> fields = new HashMap<>();
		fields.put("required", required);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("template_id", templateId);
		maps.put("fields", fields);
		
		return JsonMapper.nonDefaultMapper().toJson(maps);
	}
	
	/**
	 * 领卡链接 TODO
	 */
	public static AlipayMarketingCardActivateurlApplyModel alipayMarketingCardActivateurlApplyModel(String templateId, String url) {
		AlipayMarketingCardActivateurlApplyModel model = new AlipayMarketingCardActivateurlApplyModel();
		model.setTemplateId(templateId);
		model.setOutString("alipay");
		model.setCallback(url);
		return model;
	}

    /**
     * 活动创建接口 TODO
     */
    public static KoubeiMarketingCampaignActivityCreateModel koubeiMarketingCampaignActivityCreateModel() {
        Calendar max = Calendar.getInstance();
        KoubeiMarketingCampaignActivityCreateModel model = new KoubeiMarketingCampaignActivityCreateModel();
        model.setOutBizNo(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(max.getTime()));
//        model.setOperatorId(operatorId); 不填时默认是商户
//        model.setOperatorType(operatorType); 不填时默认是商户
        model.setName("注册活动首发。。。啦啦啦");
        model.setStartTime(max.getTime());
        max.set(Calendar.YEAR, max.get(Calendar.YEAR) + 1);// 有效期默认1年
        model.setEndTime(max.getTime());
        model.setType("DIRECT_SEND");
        model.setDesc("该活动是用于挽回流失用户的");
        
        BudgetInfo budgetInfo = new BudgetInfo(); // 活动预算
        budgetInfo.setBudgetTotal("20");
        budgetInfo.setBudgetType("QUANTITY");
        model.setBudgetInfo(budgetInfo);
        
        ConstraintInfo constraintInfo = new ConstraintInfo(); // 活动限制信息
        constraintInfo.setUserWinCount(String.valueOf(55));
        constraintInfo.setUserWinFrequency("D||55");
//        constraintInfo.setCrowdGroupId(crowdGroupId);// 人群规则组ID 
//        constraintInfo.setCrowdRestriction(crowdRestriction);// 针对指定人群的约束条件
        List<String> suitShops = new ArrayList<>();
//        suitShops.add("2017111500077000000046464829");
        suitShops.add("2017052300077000000000097924");
        
        constraintInfo.setSuitShops(suitShops);// 活动适用的门店列表  TODO 
//        constraintInfo.setMinCost(minCost);// 最低消费金额
//        constraintInfo.setItemIds(itemIds);// 单品码列表 
        model.setConstraintInfo(constraintInfo);
        
        List<PromoTool> promoTools = new ArrayList<>();
        PromoTool promoTool = new PromoTool();
        Voucher voucher = new Voucher();
        voucher.setType("MONEY");
//        voucher.setVerifyMode(verifyMode);
        voucher.setVoucherNote("券的备注券的备注券的备注券的备注"); // 券发出后核销时将在当面付接口将该值传回，供收银系统识别   券的备注
        
        List<VoucherDescDetail> descDetailList = new ArrayList<>();
        VoucherDescDetail voucherDescDetail = new VoucherDescDetail();
        voucherDescDetail.setTitle("券说明的标题券说明的标题券说明的标题");
        List<String> details = new ArrayList<>();
        details.add("巴拉。。");
        details.add("巴拉巴拉。。");
        details.add("巴拉巴拉巴拉。。");
        voucherDescDetail.setDetails(details);
        List<String> images = new ArrayList<>();
        images.add("https://dl.django.t.taobao.com/rest/1.0/image?fileIds=FfviK_x7S6yESp2zyAIZawAAACMAAQED&zoom=original");
        voucherDescDetail.setImages(images);
        voucherDescDetail.setUrl("http://blmdz.xyz");
        
//        descDetailList.add(voucherDescDetail);
        voucher.setDescDetailList(descDetailList);
        
        voucher.setMultiUseMode("NO_MULTI"); // 券叠加的属性，NO_MULTI:不可叠加;MULTI_USE_WITH_SINGLE:全场优惠和单品优惠的叠加；MULTI_USE_WITH_OTHERS:全场和其他所有优惠都可以叠加
//        └ rounding_rule String  可选  100 券核销时，抹零方式，目前支持： 
//        NOT_AUTO_ROUNDING:不自动抹零 
//        AUTO_ROUNDING_YUAN:自动抹零到元 
//        AUTO_ROUNDING_JIAO:自动抹零到角 
//        ROUNDING_UP_YUAN:四舍五入到元 
//        ROUNDING_UP_JIAO:四舍五入到角 NOT_AUTO_ROUNDING
        voucher.setName("券的名称券的名称");
        voucher.setBrandName("券副标题券副标题券副标题");
        List<String> useInstructions = new ArrayList<>();
        useInstructions.add("巴拉说明。。");
        useInstructions.add("巴拉巴拉说明。。");
        useInstructions.add("巴拉巴拉巴拉说明。。");
        voucher.setUseInstructions(useInstructions);
        voucher.setLogo("XR-tC_4jSmO-hE_UrNhp-wAAACMAAQED");
        voucher.setVoucherImg("FfviK_x7S6yESp2zyAIZawAAACMAAQED");
        voucher.setMaxAmount("20");// 最高金额
        voucher.setWorthValue("20");// 减金额
        voucher.setDonateFlag(String.valueOf(false));// 是否可以转赠
        voucher.setValidateType("FIXED");// 有效期类型
        voucher.setStartTime(new Date());
        voucher.setEndTime(max.getTime());
        voucher.setRelativeTime("100");
        voucher.setDesc("券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明券的详细说明");
        
        UseRule useRule = new UseRule();
        useRule.setSuitShops(suitShops);// 券适用门店列表 TODO
        useRule.setMinConsume(String.valueOf(30)); // 最低消费
        useRule.setLimitRule("USE_NO_LIMIT");
        voucher.setUseRule(useRule);
        
        DisplayConfig displayConfig = new DisplayConfig();
        displayConfig.setSlogan("券的宣传语券的宣传语");
        displayConfig.setSloganImg("1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC");
        voucher.setDisplayConfig(displayConfig);
        
        List<ClauseTerm> clauseTerms = new ArrayList<>();
        ClauseTerm clauseTerm = new ClauseTerm();
        clauseTerm.setTitle("券的说明条款title");
        List<String> descriptions = new ArrayList<>();
        descriptions.add("巴拉说明条款。。");
        descriptions.add("巴拉巴拉说明条款。。");
        descriptions.add("巴拉巴拉巴拉说明条款。。");
        clauseTerm.setDescriptions(descriptions);
        clauseTerms.add(clauseTerm);
        voucher.setClauseTerms(clauseTerms);
        
        voucher.setEffectType("IMMEDIATELY");
        
        promoTool.setVoucher(voucher);
        
        promoTool.setStatus("STARTED"); //单个营销工具的生效状态，当在招商部分券失效后会使用这个字段
//        promoTool.setVoucherNo(voucherNo);// 营销工具uid,创建营销活动时无需设置
        SendRule sendRule = new SendRule();
        sendRule.setAllowRepeatSend("false");
        promoTool.setSendRule(sendRule);
        promoTools.add(promoTool);
        
        model.setPromoTools(promoTools);
        
        List<PublishChannel> publishChannels = new ArrayList<>();
        PublishChannel publishChannel = new PublishChannel();
        publishChannel.setType("SHORT_LINK");
        publishChannel.setName("短连接投放短连接投放");
        publishChannels.add(publishChannel);
        
        model.setPublishChannels(publishChannels);
        
        return model;
    }
    
}






























