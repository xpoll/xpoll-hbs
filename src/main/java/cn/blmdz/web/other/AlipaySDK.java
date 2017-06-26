package cn.blmdz.web.other;

import java.io.File;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOfflineMaterialImageUploadRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOfflineMaterialImageUploadResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

import cn.blmdz.hbs.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlipaySDK {

	private AlipayClient client;

	public AlipaySDK(AlipayClient alipayClient) {
		this.client = alipayClient;
	}

	/**
	 * 图片上传 alipay.offline.material.image.upload
	 */
	public AlipayOfflineMaterialImageUploadResponse upload(File file) {
		log.info("-----------------图片上传-------------------");
		try {
			AlipayOfflineMaterialImageUploadRequest request = new AlipayOfflineMaterialImageUploadRequest();
			request.setImageName(file.getName().substring(0, file.getName().lastIndexOf(".")));
			request.setImageType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
			request.setImageContent(new FileItem(file));
			AlipayOfflineMaterialImageUploadResponse response = client.execute(request);
			log.info(response.getBody());
			if (response.isSuccess()) {
				log.info("success.");
			} else {
				log.error("error.");
			}
			return response;
		} catch (AlipayApiException e) {
			throw new GlobalException(e.getCause());
		}
	}

	/**
	 * 获取用户授权 alipay.system.oauth.token
	 */
	public AlipaySystemOauthTokenResponse userToken(String code) {
		log.info("-----------------获取用户授权-------------------");
		try {
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("authorization_code");
			request.setCode(code);
			AlipaySystemOauthTokenResponse response = client.execute(request);
			log.info(response.getBody());
			if (response.isSuccess()) {
				log.info("success.");
			} else {
				log.error("error.");
			}
			return response;
		} catch (AlipayApiException e) {
			throw new GlobalException(e.getCause());
		}
	}

	/**
	 * 刷新用户授权 alipay.system.oauth.token
	 */
	public AlipaySystemOauthTokenResponse refreshUser(String refreshToken) {
		log.info("-----------------刷新用户授权-------------------");
		try {
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("refresh_token");
			request.setRefreshToken(refreshToken);
			AlipaySystemOauthTokenResponse response = client.execute(request);
			log.info(response.getBody());
			if (response.isSuccess()) {
				log.info("success.");
			} else {
				log.error("error.");
			}
			return response;
		} catch (AlipayApiException e) {
			throw new GlobalException(e.getCause());
		}
	}

	/**
	 * 查询用户授权信息 alipay.user.info.share
	 */
	public AlipayUserInfoShareResponse userInfo(String authToken) {
		log.info("-----------------查询用户授权信息-------------------");
		try {
			AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
			AlipayUserInfoShareResponse response = client.execute(request, authToken);
			log.info(response.getBody());
			if (response.isSuccess()) {
				log.info("success.");
			} else {
				log.error("error.");
			}
			return response;
		} catch (AlipayApiException e) {
			throw new GlobalException(e.getCause());
		}
	}
}
