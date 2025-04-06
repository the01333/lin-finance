package com.puxinxiaolin.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.puxinxiaolin.common.exception.ParameterException;
import com.puxinxiaolin.wx.aes.AesException;
import com.puxinxiaolin.wx.config.WxConfig;
import com.puxinxiaolin.wx.dto.MpBaseEventRequest;
import com.puxinxiaolin.wx.dto.MpCommonRequest;
import com.puxinxiaolin.wx.dto.MpSubscribeEventRequest;
import com.puxinxiaolin.wx.dto.MpTextEventRequest;
import com.puxinxiaolin.wx.service.WxMpEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpEventServiceImpl implements WxMpEventService {
    final WxConfig wxConfig;
    final ApplicationContext applicationContext;

    /**
     * 接收公众号事件
     *
     * @param mpCommonRequest
     * @param httpServletRequest
     * @return
     */
    @Override
    public String receiveMpEvent(MpCommonRequest mpCommonRequest, HttpServletRequest httpServletRequest) throws AesException, IOException {
        checkSignature(mpCommonRequest.getSignature(),
                wxConfig.getMp().getToken(),
                mpCommonRequest.getTimestamp(),
                mpCommonRequest.getNonce(),
                null);

        // 如果是Get请求直接返回wx服务器传进来的echostr，否则继续往下处理
        if (StringUtils.isBlank(httpServletRequest.getHeader("content-type"))) {
            log.info("WxMpEventServiceImpl.receiveMpEvent.content-type is null");
            return mpCommonRequest.getEchostr();
        }
        log.info("WxMpEventServiceImpl.receiveMpEvent.content-type:{}", httpServletRequest.getHeader("content-type"));
        if (log.isInfoEnabled()) {
            log.info("WxMpEventServiceImpl.receiveMpEvent.mpCommonRequest:{}", JSON.toJSONString(mpCommonRequest));
        }

        XmlMapper xmlMapper = new XmlMapper();
        Object obj = xmlMapper.readValue(httpServletRequest.getInputStream(), Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        if (log.isInfoEnabled()) {
            log.info("WxMpEventServiceImpl.receiveMpEvent.obj:{}", JSON.toJSONString(obj));
        }

        MpBaseEventRequest mpBaseEventRequest = objectMapper.convertValue(obj, MpBaseEventRequest.class);
        if ("text".equals(mpBaseEventRequest.getMsgType())) {
            MpTextEventRequest mpTextEventRequest = objectMapper.convertValue(obj, MpTextEventRequest.class);
            if (log.isInfoEnabled()) {
                log.info("WxMpEventServiceImpl.receiveMpEvent.mpTextEventRequest:{}", JSON.toJSONString(mpTextEventRequest));
            }
            log.info("WxMpEventServiceImpl.receiveMpEvent.mpTextEventRequest is pushed...");
            applicationContext.publishEvent(mpTextEventRequest);
        }
        if ("event".equals(mpBaseEventRequest.getMsgType())) {
            MpSubscribeEventRequest mpSubscribeEventRequest = objectMapper.convertValue(obj, MpSubscribeEventRequest.class);
            if (log.isInfoEnabled()) {
                log.info("WxMpEventServiceImpl.receiveMpEvent.mpSubscribeEventRequest:{}", JSON.toJSONString(mpSubscribeEventRequest));
            }
            log.info("WxMpEventServiceImpl.receiveMpEvent.mpSubscribeEventRequest is pushed...");
            applicationContext.publishEvent(mpSubscribeEventRequest);
        }

        log.info("WxMpEventServiceImpl.receiveMpEvent.push event finished!");
        return mpCommonRequest.getEchostr() == null ? "success" : mpCommonRequest.getEchostr();
    }

    /**
     * 用SHA1算法生成安全签名并校验
     *
     * @param signature 签名
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param encrypt   密文
     * @return 安全签名
     * @throws AesException
     */
    @Override
    public void checkSignature(String signature, String token, String timestamp,
                               String nonce, String encrypt) throws AesException {
        try {
            if (StringUtils.isBlank(signature) || StringUtils.isBlank(token)
                    || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)) {
                throw new ParameterException("签名参数非法");
            }

            String[] array;
            if (StringUtils.isBlank(encrypt)) {
                array = new String[]{token, timestamp, nonce};
            } else {
                array = new String[]{token, timestamp, nonce, encrypt};
            }

            StringBuffer sb = new StringBuffer();
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();

            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex;
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }

            log.info("WxMpEventServiceImpl.checkSignature.wx_signature:{}, my_signature:{}", signature, hexstr);
            if (!Objects.equals(signature, hexstr.toString())) {
                throw new AesException(AesException.ValidateSignatureError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ComputeSignatureError);
        }
    }

}
