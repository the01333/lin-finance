package com.puxinxiaolin.common.service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthFilterService<T> {

    /**
     * 过滤器拦截
     *
     * @param request
     * @param response
     * @param filterChain
     */
    void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain);

    /**
     * 不经过过滤器筛选
     *
     * @param request
     * @return
     * @throws ServletException
     */
    boolean shouldNotFilter(HttpServletRequest request);

}
