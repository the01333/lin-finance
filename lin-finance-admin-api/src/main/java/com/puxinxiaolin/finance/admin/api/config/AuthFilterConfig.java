package com.puxinxiaolin.finance.admin.api.config;

import com.puxinxiaolin.common.service.AuthFilterService;
import com.puxinxiaolin.finance.biz.dto.AdminDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 过滤器配置
 * @Author: YCcLin
 * @Date: 2025/4/8 21:18
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class AuthFilterConfig extends OncePerRequestFilter {
    final AuthFilterService<AdminDTO> authFilterService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authFilterService.doFilterInternal(request, response, filterChain);
    }

    /**
     * 不经过过滤器筛选
     *
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return authFilterService.shouldNotFilter(request);
    }

}
