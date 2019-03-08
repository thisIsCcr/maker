package com.crsm.maker.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crsm.maker.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<String> getRoleId(@Param("userId")Integer userId);

}
