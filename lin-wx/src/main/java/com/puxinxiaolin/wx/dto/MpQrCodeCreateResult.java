package com.puxinxiaolin.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 公众号二维码创建的返回参数
 * 详情见文档: https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
 * @Author: YCcLin
 * @Date: 2025/4/5 23:10
 */
@Data
public class MpQrCodeCreateResult implements Serializable {

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
     */
    @JsonProperty(value = "expire_seconds")
    private Integer expireSeconds;

    /**
     * 错误码
     */
    @JsonProperty(value = "errcode")
    private String errCode;

    /**
     * 错误信息
     */
    @JsonProperty(value = "errmsg")
    private String errMsg;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

    /**
     * 获取的二维码ticket，凭借此 ticket 可以在有效时间内换取二维码。
     */
    private String ticket;

}