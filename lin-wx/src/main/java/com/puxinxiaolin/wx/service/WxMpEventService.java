package com.puxinxiaolin.wx.service;

import com.puxinxiaolin.wx.aes.AesException;
import com.puxinxiaolin.wx.dto.MpCommonRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface WxMpEventService {

    /**
     * 接收公众号事件
     *
     * @param mpCommonRequest
     * @param httpServletRequest
     * @return
     */
    String receiveMpEvent(MpCommonRequest mpCommonRequest, HttpServletRequest httpServletRequest) throws AesException, IOException;

    /**
     * 用SHA1算法生成安全签名并校验
     *
     * @param signature
     * @param token
     * @param timestamp
     * @param nonce
     * @param encrypt
     */
    void checkSignature(String signature, String token, String timestamp, String nonce, String encrypt) throws AesException;

}
