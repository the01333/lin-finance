package com.puxinxiaolin.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.puxinxiaolin.finance.biz.dto.form.GetBase64CodeForm;
import com.puxinxiaolin.finance.biz.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {

    /**
     * 获取客户端 ID
     *
     * @return
     */
    @Override
    public String getClientId() {
        return UUID.randomUUID().toString()
                .replace("-", "");
    }

    /**
     * 获取图形验证码
     *
     * @param form
     * @return
     */
    @Override
    public String getBase64Code(GetBase64CodeForm form) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(300, 192, 5, 1000);
        String code = lineCaptcha.getCode();
        // TODO [YCcLin 2025/3/31]: 验证码保存到 Redis
        return lineCaptcha.getImageBase64();
    }

}
