package cn.blmdz.web.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.alipay.api.domain.AlipayMarketingCardOpenModel;
import com.alipay.api.domain.CardUserInfo;
import com.alipay.api.domain.MerchantCard;
import com.alipay.api.domain.MerchantMenber;
import com.alipay.api.response.AlipayMarketingCardActivateformQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

import cn.blmdz.web.manager.ThirdManager;
import cn.blmdz.web.model.AlipayUserInfo;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.third.AliPayConfig;
import cn.blmdz.web.third.AlipaySDK;

@Component("alipayThirdManager")
public class AlipayThirdManager implements ThirdManager {

    private AlipaySDK alipaySDK = AliPayConfig.getInstance();

    @Override
    public ThirdUser getThirdUserId(String authCode, ThirdUser tuser) {
        AlipaySystemOauthTokenResponse respToken = alipaySDK.userToken(authCode);
        if (respToken.isSuccess()) {
            AlipayUserInfoShareResponse respInfo = alipaySDK.userInfo(respToken.getAccessToken());
            if (respInfo.isSuccess()) {
                tuser.setNick(respInfo.getNickName());
                tuser.setThirdUserId(respInfo.getUserId());
                tuser.setAvatar(respInfo.getAvatar());
                return tuser;
            }
        }
        return null;
    }

    @Override
    public void card(String requestId, String templateId, String authCode) {
        AlipaySystemOauthTokenResponse respToken = alipaySDK.userToken(authCode);
        AlipayMarketingCardActivateformQueryResponse resp = alipaySDK.memberCardForm(requestId, templateId,
                respToken.getAccessToken());
        AlipayUserInfo infos = new AlipayUserInfo().init(resp.getInfos());

        Calendar max = Calendar.getInstance();
        AlipayMarketingCardOpenModel model = new AlipayMarketingCardOpenModel();
        model.setOutSerialNo(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(max.getTime())
                + String.format("%06d", (int) (Math.random() * 1000000)));
        model.setCardTemplateId(templateId);

        CardUserInfo cardUserInfo = new CardUserInfo();
        cardUserInfo.setUserUniIdType("UID");// 默认
        cardUserInfo.setUserUniId(respToken.getUserId());
        model.setCardUserInfo(cardUserInfo);
        MerchantCard cardExtInfo = new MerchantCard();
        cardExtInfo.setOpenDate(max.getTime());
        max.set(Calendar.YEAR, max.get(Calendar.YEAR) + 100);// 有效期默认100年

        cardExtInfo.setValidDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(max.getTime()));// 默认格式
        cardExtInfo.setExternalCardNo("880000" + String.format("%06d", (int) (Math.random() * 1000000)));
        model.setCardExtInfo(cardExtInfo);

        MerchantMenber memberExtInfo = new MerchantMenber();
        memberExtInfo.setName(infos.getName());
        memberExtInfo.setGende(infos.getGender());
        memberExtInfo.setCell(infos.getMobile());
        memberExtInfo.setBirth(infos.getBirthday());
        model.setMemberExtInfo(memberExtInfo);
        alipaySDK.memberCardOpen(model, respToken.getAccessToken());
    }

    @Override
    public String cardLink() {
        return AliPayConfig.url;
    }
}
