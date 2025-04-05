package com.puxinxiaolin.wx.service.impl;

import com.puxinxiaolin.wx.constant.WXApiConstant;
import com.puxinxiaolin.wx.dto.AccessTokenResult;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateRequest;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateResult;
import com.puxinxiaolin.wx.service.WXService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WXServiceImpl implements WXService {
    final WebClient webClient;
    // 接口重试次数
    int retry = 3;

    /**
     * 获取公众号 token
     *
     * @param appid
     * @param secret
     * @return
     */
    @Override
    public AccessTokenResult getMpAccessToken(String appid, String secret) {
        String url = String.format(WXApiConstant.ACCESS_TOKEN_API, appid, secret);
        return webClient.get()
                .uri(url).retrieve()
                .bodyToMono(AccessTokenResult.class).retry(retry)
                .block();
    }

    /**
     * 生成临时公众号二维码
     *
     * @param token
     * @param request
     * @return
     */
    @Override
    public MpQrCodeCreateResult createMpQrcodeCreate(String token, MpQrCodeCreateRequest request) {
        String url = String.format(WXApiConstant.MP_QRCODE_CREATE, token);
        MpQrCodeCreateResult result = webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request)).retrieve()
                .bodyToMono(MpQrCodeCreateResult.class).retry(retry)
                .block();
        if (Objects.isNull(result) || StringUtils.isBlank(result.getTicket())) {
            return result;
        }

        // 详情见文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
        result.setQrCodeUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + result.getTicket());
        return result;
    }
}
