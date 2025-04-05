package com.puxinxiaolin.wx.constant;

/**
 * @Description: 微信三方 API 常量类
 * @Author: YCcLin
 * @Date: 2025/4/5 23:17
 */
public class WXApiConstant {

    /**
     * 获取微信token，请求url：https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=`%s`&secret=`%s`
     * 详情见文档: https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     */
    public static final String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 生成临时公众号二维码，请求url：https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=`%s`
     * 详情见文档: https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
     */
    public static final String MP_QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

}
