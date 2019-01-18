package com.crsm.maker.resourcesFile.mapper;

import com.crsm.maker.resourcesFile.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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

}
