package com.puxinxiaolin.wx;

import com.puxinxiaolin.wx.dto.AccessTokenResult;
import com.puxinxiaolin.wx.service.WXService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Slf4j
@SpringBootTest
public class WXTest {

    @Resource
    private WXService wxService;

    @Test
    public void TestGetMpAccessToken() {
        AccessTokenResult accessTokenResult = getMpAccessToken();
        System.out.println(accessTokenResult.toString());
    }

    /**
     * 除了要在公众号配置中获取appid、配置密钥外，还需要配置好白名单才能成功调用
     *
     * @return
     */
    private AccessTokenResult getMpAccessToken() {
        AccessTokenResult accessTokenResult = wxService.getMpAccessToken("wx9e24917b9886a8df", "98e75761afcc2fcbc13ffaadacb5ab06");

        return accessTokenResult;
    }

}
