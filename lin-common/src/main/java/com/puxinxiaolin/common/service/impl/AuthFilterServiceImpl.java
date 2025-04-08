package com.puxinxiaolin.common.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puxinxiaolin.common.config.SecurityConfig;
import com.puxinxiaolin.common.constant.CommonConstant;
import com.puxinxiaolin.common.dto.BaseUserInfoDTO;
import com.puxinxiaolin.common.exception.BizException;
import com.puxinxiaolin.common.exception.LoginException;
import com.puxinxiaolin.common.service.AuthFilterService;
import com.puxinxiaolin.common.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 过滤器处理
 * @Author: YCcLin
 * @Date: 2025/4/8 21:13
 */
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
        // 如果是管理员不检查权限，拥有所有权限
        if (sysRoleIds.contains(CommonConstant.ROLE_ADMIN)) {
            return;
        }

        Set<Integer> roleMenuIds = listRoleMenuIdByCache(sysRoleIds);
        if (CollectionUtils.isEmpty(roleMenuIds)) {
            throw new BizException("角色对应的菜单id不存在");
        }

        List<String> menuPathList = listMenuPathByCache(roleMenuIds);
        if (CollectionUtils.isEmpty(menuPathList)) {
            throw new BizException("角色菜单配置不存在");
        }

        for (String menuPath : menuPathList) {
            if (antPathMatcher.match(menuPath, path)) {
                return;
            }
        }
        throw new BizException("非法访问");
    }

    /**
     * 从缓存中获取菜单路径
     *
     * @param menuIds
     * @return
     */
    private List<String> listMenuPathByCache(Set<Integer> menuIds) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.multiGet("MENU", menuIds.stream()
                .map(String::valueOf)
                .collect(Collectors.toList())
        );
    }

    /**
     * 从缓存中获取角色对应的菜单id
     *
     * @param roleIds
     * @return
     */
    private Set<Integer> listRoleMenuIdByCache(Set<Long> roleIds) {
        HashOperations<String, String, Set<Integer>> hashOps = redisTemplate.opsForHash();
        List<Set<Integer>> roleMenuIds = hashOps.multiGet("ROLE", roleIds.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet())
        );

        // 对结果进行处理，List<Set<Integer>> -> Set<Integer>
        return roleMenuIds.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
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
        if (securityConfig == null || !securityConfig.getEnable()
                || CollectionUtils.isEmpty(securityConfig.getIgnores())) {
            return false;
        }

        String path = request.getServletPath();
        boolean ignore = securityConfig.getIgnores().stream()
                // 任意一个满足即可
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
        if (log.isDebugEnabled()) {
            log.info("AuthFilterServiceImpl.shouldNotFilter.path:{}, [ignore: {}]", path, ignore);
        }
        return ignore;
    }

}
