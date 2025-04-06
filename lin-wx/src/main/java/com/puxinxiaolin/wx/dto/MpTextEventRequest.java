package com.puxinxiaolin.wx.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 接收微信文本消息类型
 * 具体参数意思参考文档地址：
 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MpTextEventRequest extends MpBaseEventRequest {

    @JsonProperty(value = "Content")
    private String content;

}