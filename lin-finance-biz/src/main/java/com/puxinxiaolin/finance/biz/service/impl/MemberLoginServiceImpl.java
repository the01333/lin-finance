package com.puxinxiaolin.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.puxinxiaolin.common.exception.BizException;
import com.puxinxiaolin.common.exception.ParameterException;
import com.puxinxiaolin.common.util.DateUtil;
import com.puxinxiaolin.common.util.MyUtil;
import com.puxinxiaolin.finance.biz.constant.RedisKeyConstant;
import com.puxinxiaolin.finance.biz.domain.MemberBindPhone;
import com.puxinxiaolin.finance.biz.dto.form.GetBase64CodeForm;
import com.puxinxiaolin.finance.biz.dto.form.GetSmsCodeForm;
import com.puxinxiaolin.finance.biz.dto.form.SmsCodeResult;
import com.puxinxiaolin.finance.biz.enums.SmsCodeEnum;
import com.puxinxiaolin.finance.biz.service.MemberBindPhoneService;
import com.puxinxiaolin.finance.biz.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {
    final RedisTemplate<String, Object> redisTemplate;
    final MemberBindPhoneService memberBindPhoneService;

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
        // 图形验证码 -> Redis, pattern => GRAPHIC_VERIFICATION_CODE:clientId:code
        redisTemplate.opsForValue()
                .set(RedisKeyConstant.GRAPHIC_VERIFICATION_CODE + form.getClientId(),
                        code, 15, TimeUnit.MINUTES);

        return lineCaptcha.getImageBase64();
    }

    /**
     * 获取短信验证码
     *
     * @param form
     */
    @Override
    public void sendSmsCode(GetSmsCodeForm form) {
        // 校验图形验证码
        checkBase64Code(form.getClientId(), form.getCode());

        String key = RedisKeyConstant.SMS_CODE + form.getSmsCodeType() + form.getPhone();
        SmsCodeResult smsCodeResult = (SmsCodeResult) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(smsCodeResult)) {
            Duration duration = DateUtil.getDuration(smsCodeResult.getGetTime(), DateUtil.getSystemTime());
            if (duration.getSeconds() < 60) {
                throw new BizException("验证码获取频繁，请稍后重试");
            }
        }

        MemberBindPhone memberBindPhone = memberBindPhoneService.getMemberByPhone(form.getPhone());
        if (form.getSmsCodeType().equals(SmsCodeEnum.REG.getCode()) && Objects.nonNull(memberBindPhone)) {
            throw new ParameterException("phone", "该手机号已注册");
        }
        if (form.getSmsCodeType().equals(SmsCodeEnum.LOGIN.getCode()) && Objects.isNull(memberBindPhone)) {
            throw new ParameterException("phone", "该手机号未注册");
        }

        int smsCode = MyUtil.getRandom(6);
        SmsCodeResult result = new SmsCodeResult();
        result.setCode(String.valueOf(smsCode));
        result.setGetTime(DateUtil.getSystemTime());
        redisTemplate.opsForValue()
                .set(key, result, 15, TimeUnit.MINUTES);
        log.info("MemberLoginServiceImpl.sendSmsCode.clientId:{}, phone:{}, smsCode:{}",
                form.getClientId(), form.getPhone(), smsCode);

        // TODO [YCcLin 2025/4/1]: 调用第三方短信发送服务
    }

    /**
     * 校验图形验证码
     *
     * @param clientId
     * @param code
     * @return
     */
    private Boolean checkBase64Code(String clientId, String code) {
        String key = RedisKeyConstant.GRAPHIC_VERIFICATION_CODE + clientId;
        String value = (String) redisTemplate.opsForValue()
                .get(key);
        redisTemplate.delete(key);

        if (!code.equalsIgnoreCase(value)) {
            throw new ParameterException("code", "图形验证码错误");
        }
        return true;
    }
}
