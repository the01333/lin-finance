package com.puxinxiaolin.common.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puxinxiaolin.common.config.SecurityConfig;
import com.puxinxiaolin.common.dto.BaseUserInfoDTO;
import com.puxinxiaolin.common.exception.LoginException;
import com.puxinxiaolin.common.service.AuthFilterService;
import com.puxinxiaolin.common.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "sys", name = "enable-my-security", havingValue = "true")
@RequiredArgsConstructor
public class AuthFilterServiceImpl<T> implements AuthFilterService<T> {
    final TokenService<T> tokenService;
    final AntPathMatcher antPathMatcher;
    final SecurityConfig securityConfig;
    final ObjectMapper objectMapper;
    final HandlerExceptionResolver handlerExceptionResolver;
    final RedisTemplate<String, Object> redisTemplate;

    /**
     * 过滤器拦截
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain) {
        try {
            if (securityConfig == null || !securityConfig.getEnable()) {
                filterChain.doFilter(request, response);
                return;
            }

            T userInfo = null;
            if ("token".equals(securityConfig.getGetUserType())) {
                String token = request.getHeader("api-access-token");
                userInfo = tokenService.checkToken(token);
            }
            if ("gateway".equals(securityConfig.getGetUserType())) {
                String userInfoJson = request.getHeader("user");
                userInfo = objectMapper.readValue(userInfoJson, new TypeReference<T>() {
                });
            }

            if (userInfo == null) {
                throw new LoginException("无法获取到用户信息");
            }
            BaseUserInfoDTO dto = (BaseUserInfoDTO) userInfo;

            // 检查权限
            checkPermissions(dto.getSysRoleIds(), request.getServletPath());
            // T userInfo = userService.getRedisUser(tokenResponse.getToken());
            // 用户信息存储在线程中
            tokenService.setThreadLocalUser(userInfo);
            filterChain.doFilter(request, response);
            tokenService.removeThreadLocalUser();

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    /**
     * 检查权限
     *
     * @param sysRoleIds
     * @param path
     */
    public void checkPermissions(Set<Long> sysRoleIds, String path) {

    }

    /**
     * 不经过过滤器筛选
     *
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        // TODO [YCcLin 2025/4/7]: https://www.yuque.com/wangpaixuexiao/lah721/vo5o3l94qe2a5w13
    }

}
