package com.crsm.maker.user.mapper;

import com.crsm.maker.user.entity.RoleRms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-01-25
 */
@Component
@Mapper
public interface RoleRmsMapper extends BaseMapper<RoleRms> {

    /**
     * 获取角角色Id
     * @param roleIds
     * @return
     */
    public List<String> getRmsId(@Param("roleIds")List<String> roleIds);
}
