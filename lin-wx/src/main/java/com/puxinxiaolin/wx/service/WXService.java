package com.puxinxiaolin.wx.service;

import com.puxinxiaolin.wx.dto.AccessTokenResult;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateRequest;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateResult;

public interface WXService {

    /**
     * 获取公众号 token
     *
     * @param appid
     * @param secret
     * @return
     */
    AccessTokenResult getMpAccessToken(String appid, String secret);

    /**
     * 生成临时公众号二维码
     * @param token
     * @param request
     * @return
     */
    MpQrCodeCreateResult createMpQrcodeCreate(String token, MpQrCodeCreateRequest request);

}
