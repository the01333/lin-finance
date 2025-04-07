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
        AccessTokenResult accessTokenResult = wxService.getMpAccessToken("wx9e24917b9886a8df", "f5a1256586d75429efeb8ccb5fdd9d2b");
//        AccessTokenResult accessTokenResult = wxService.getMpAccessToken("wx66eb4ba1e78feff0", "333225363f6789756a61f24010dd4291");

        return accessTokenResult;
    }

}
