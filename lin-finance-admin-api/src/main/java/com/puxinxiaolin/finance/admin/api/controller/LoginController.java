package com.puxinxiaolin.finance.admin.api.controller;

import com.puxinxiaolin.common.dto.ApiResponse;
import com.puxinxiaolin.finance.biz.dto.form.GetBase64CodeForm;
import com.puxinxiaolin.finance.biz.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户登录模块")
@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    final MemberLoginService memberLoginService;

    /**
     * 获取图形验证码
     *
     * @param form
     * @return
     * @tips: 这里参数的 @ModelAttribute 是用于映射前端提交的表单数据, 而 @RequestBody 是用于映射前端提交的 JSON 数据
     */
    @ApiOperation(value = "获取图形验证码")
    @GetMapping("/getBase64Code")
    public ApiResponse<String> getBase64Code(@Validated @ModelAttribute GetBase64CodeForm form) {
        return ApiResponse.success(memberLoginService.getBase64Code(form));
    }

    /**
     * 获取客户端 ID
     *
     * @return
     */
    @ApiOperation(value = "获取客户端 ID")
    @GetMapping("/getClientId")
    public ApiResponse<String> getClientId() {
        return ApiResponse.success(memberLoginService.getClientId());
    }

    /**
     * 获取短信验证码
     */

}
