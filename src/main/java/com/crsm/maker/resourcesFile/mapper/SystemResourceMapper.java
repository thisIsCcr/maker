package com.crsm.maker.resourcesFile.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crsm.maker.resourcesFile.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
@Mapper
@Component
public interface SystemResourceMapper extends BaseMapper<SysResource> {

     IPage<SysResource> selectPageVo(Page page,@Param("sysSue")SysResource sysSue);

     Integer saveAndReturnId(@Param("sysSue")SysResource sysSue);

     List<SysResource> getAllMusicInfo(@Param(Constants.WRAPPER) Wrapper wrapper);

}
