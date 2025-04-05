package com.puxinxiaolin.finance.admin.api.controller;

import com.puxinxiaolin.common.dto.ApiResponse;
import com.puxinxiaolin.finance.biz.dto.form.GenerateMpRegCodeForm;
import com.puxinxiaolin.finance.biz.dto.form.PhoneRegisterForm;
import com.puxinxiaolin.finance.biz.dto.vo.GenerateMpRegCodeVo;
import com.puxinxiaolin.finance.biz.service.MemberRegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户注册模块")
@RestController
@RequestMapping(value = "/reg")
@RequiredArgsConstructor
@Slf4j
public class RegController {
    final MemberRegService memberRegService;

    /**
     * 手机号注册
     *
     * @param form
     * @return
     */
    @ApiOperation(value = "手机号注册")
    @PostMapping("/phoneReg")
    public ApiResponse<Long> phoneReg(@Validated @RequestBody PhoneRegisterForm form) {
        return ApiResponse.success(memberRegService.phoneReg(form));
    }

    @ApiOperation(value = "生成微信公众号二维码（关注注册）")
    @GetMapping("/generateMpRegCode")
    public ApiResponse<GenerateMpRegCodeVo> generateMpRegCode(@Validated @ModelAttribute GenerateMpRegCodeForm form) {
        return ApiResponse.success(memberRegService.generateMpRegCode(form.getClientId()));
    }

}
