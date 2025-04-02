package com.puxinxiaolin.finance.biz.service.impl;

import com.puxinxiaolin.common.exception.BizException;
import com.puxinxiaolin.finance.biz.domain.Tenant;
import com.puxinxiaolin.finance.biz.mapper.TenantMapper;
import com.puxinxiaolin.finance.biz.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {
    final TenantMapper tenantMapper;

    /**
     * 创建租户
     *
     * @return
     */
    @Override
    public Long add() {
        Tenant tenant = new Tenant();
        tenant.initDefault();
        if (tenantMapper.insert(tenant) == 0) {
            throw new BizException("创建租户失败");
        }

        return tenant.getId();
    }

}
