package com.crsm.maker.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crsm.maker.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-01-21
 */
@Component
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
