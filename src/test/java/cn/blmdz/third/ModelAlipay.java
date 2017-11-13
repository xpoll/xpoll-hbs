package cn.blmdz.third;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.api.domain.AlipayMarketingCardActivateurlApplyModel;
import com.alipay.api.domain.AlipayMarketingCardTemplateCreateModel;
import com.alipay.api.domain.AlipayMarketingCardTemplateModifyModel;
import com.alipay.api.domain.MoreInfoDTO;
import com.alipay.api.domain.TemplateColumnInfoDTO;
import com.alipay.api.domain.TemplateFieldRuleDTO;
import com.alipay.api.domain.TemplateStyleInfoDTO;

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
		model.setTemplateStyleInfo(templateStyleInfo);
		
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
		model.setTemplateStyleInfo(templateStyleInfo);
		
		//栏位信息--必须
		List<TemplateColumnInfoDTO> columnInfoList = new ArrayList<>();
		TemplateColumnInfoDTO templateColumnInfoDTO = null;
		MoreInfoDTO moreInfo = null;
		
		templateColumnInfoDTO = new TemplateColumnInfoDTO();
		templateColumnInfoDTO.setCode("QXX_HOME");
		moreInfo = new MoreInfoDTO();
//		moreInfo.setParams("{\"third\":\"alipay\"}");
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
	 * 会员表单 TODO
	 */
	public static String alipayMarketingCardFormtemplateSetModel(String templateId) {

		List<String> list = new ArrayList<>();
		list.add("OPEN_FORM_FIELD_MOBILE");// 手机号
		list.add("OPEN_FORM_FIELD_GENDER");// 性别
		list.add("OPEN_FORM_FIELD_NAME");// 姓名
		list.add("OPEN_FORM_FIELD_BIRTHDAY_WITH_YEAR");// 生日
		list.add("OPEN_FORM_FIELD_EMAIL");// 邮箱
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
}
