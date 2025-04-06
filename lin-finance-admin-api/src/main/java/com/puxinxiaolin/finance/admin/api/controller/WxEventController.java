package com.puxinxiaolin.finance.admin.api.controller;

import com.puxinxiaolin.wx.dto.MpCommonRequest;
import com.puxinxiaolin.wx.service.WxMpEventService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "微信模块")
@RestController
@RequestMapping(value = "/wxEvent")
@RequiredArgsConstructor
@Slf4j
public class WxEventController {
    final WxMpEventService wxMpEventService;

    /**
     * 接收微信事件推送
     * 参考文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_event_pushes.html
     *
     * @param mpCommonRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/receiveMpEvent",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String receiveMpEvent(@Validated @ModelAttribute MpCommonRequest mpCommonRequest,
                                 HttpServletRequest httpServletRequest) {
        return wxMpEventService.receiveMpEvent(mpCommonRequest, httpServletRequest);
    }

}
