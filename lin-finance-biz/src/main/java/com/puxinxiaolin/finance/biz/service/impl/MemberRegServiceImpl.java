package com.puxinxiaolin.finance.biz.service.impl;

import com.puxinxiaolin.common.exception.BizException;
import com.puxinxiaolin.common.exception.ParameterException;
import com.puxinxiaolin.finance.biz.config.ObjectConvertor;
import com.puxinxiaolin.finance.biz.constant.RedisKeyConstant;
import com.puxinxiaolin.finance.biz.domain.MemberBindPhone;
import com.puxinxiaolin.finance.biz.dto.form.PhoneRegisterForm;
import com.puxinxiaolin.finance.biz.dto.vo.GenerateMpRegCodeVo;
import com.puxinxiaolin.finance.biz.enums.SmsCodeEnum;
import com.puxinxiaolin.finance.biz.service.*;
import com.puxinxiaolin.wx.config.WxConfig;
import com.puxinxiaolin.wx.dto.AccessTokenResult;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateRequest;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateResult;
import com.puxinxiaolin.wx.service.WXService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRegServiceImpl implements MemberRegService {
    final MemberLoginService memberLoginService;
    final RedissonClient redissonClient;
    final MemberBindPhoneService memberBindPhoneService;
    final TransactionTemplate transactionTemplate;
    final TenantService tenantService;
    final MemberService memberService;
    final WXService wxService;
    final WxConfig wxConfig;
    final ObjectConvertor objectConvertor;

    /**
     * 手机号注册
     *
     * @param form
     * @return
     */
    @Override
    public Long phoneReg(PhoneRegisterForm form) {
        if (!Objects.equals(form.getPassword(), form.getConfirmPassword())) {
            throw new ParameterException("两次输入的密码不一致");
        }
        memberLoginService.checkSmsCode(form.getPhone(), form.getSmsCode(), SmsCodeEnum.REG.getCode());

        // 分布式锁，防止同一手机号重复注册
        RLock rLock = redissonClient.getLock(RedisKeyConstant.PHONE_CHANGE + form.getPhone());
        try {
            rLock.lock();

            MemberBindPhone memberByPhone = memberBindPhoneService.getMemberByPhone(form.getPhone());
            if (Objects.nonNull(memberByPhone)) {
                log.warn("MemberRegServiceImpl.phoneReg.phone:{} has already registered", form.getPhone());
                throw new BizException("该手机号已注册");
            }

            // 手动事务，减小锁力度
            Long memberId = transactionTemplate.execute(transactionStatus -> {
                Long tenantId = tenantService.add();
                Long id = memberService.reg(tenantId);
                if (id <= 0) {
                    throw new BizException("注册异常");
                }

                memberBindPhoneService.reg(form.getPhone(), id, form.getPassword());
                return id;
            });
            if (Objects.isNull(memberId)) {
                throw new BizException("注册失败");
            }

            return memberId;
        } catch (Exception e) {
            throw new BizException("注册失败");
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 生成微信公众号二维码（关注注册）
     *
     * @param clientId
     * @return
     */
    @Override
    public GenerateMpRegCodeVo generateMpRegCode(String clientId) {
        AccessTokenResult result = wxService.getMpAccessToken(wxConfig.getMp().getAppId(),
                wxConfig.getMp().getSecret());

        MpQrCodeCreateRequest request = new MpQrCodeCreateRequest();
        request.setExpireSeconds(wxConfig.getMp().getCodeExpire());
        request.setActionName("QR_STR_SCENE");
        request.setActionInfo(request.new ActionInfo());
        request.getActionInfo().setScene(request.new scene());
        request.getActionInfo().getScene().setSceneStr("ScanReg_" + wxConfig.getMp().getAppId() + "_" + clientId);
        MpQrCodeCreateResult response = wxService.createMpQrcodeCreate(result.getAccessToken(), request);

        return objectConvertor.toGenerateMpRegCodeResponse(response);
    }
}
