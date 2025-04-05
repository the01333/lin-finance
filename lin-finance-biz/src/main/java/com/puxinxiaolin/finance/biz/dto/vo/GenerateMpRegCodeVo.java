package com.puxinxiaolin.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenerateMpRegCodeVo {

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private String clientId;

    /**
     * 获取的二维码ticket，凭借此 ticket 可以在有效时间内换取二维码。
     */
    @ApiModelProperty(value = "获取的二维码ticket，凭借此 ticket 可以在有效时间内换取二维码。")
    private String ticket;

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
     */
    @ApiModelProperty(value = "该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。")
    private Integer expire_seconds;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    @ApiModelProperty(value = "二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片")
    private String url;

    /**
     * 二维码地址
     */
    @ApiModelProperty(value = "二维码地址")
    private String qrCodeUrl;

}
