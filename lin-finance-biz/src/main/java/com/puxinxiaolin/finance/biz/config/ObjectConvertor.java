package com.puxinxiaolin.finance.biz.config;

import com.puxinxiaolin.finance.biz.dto.vo.GenerateMpRegCodeVo;
import com.puxinxiaolin.wx.dto.MpQrCodeCreateResult;
import org.mapstruct.Mapper;

// 用这种方式，不用再加 Mappers.getMapper()
@Mapper(componentModel = "spring")
public interface ObjectConvertor {

    /**
     * MpQrCodeCreateResult -> GenerateMpRegCodeVo
     *
     * @param mpQrCodeCreateResult
     * @return
     */
    GenerateMpRegCodeVo toGenerateMpRegCodeResponse(MpQrCodeCreateResult mpQrCodeCreateResult);

}