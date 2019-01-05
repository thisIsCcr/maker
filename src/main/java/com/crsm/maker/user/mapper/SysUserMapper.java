package com.crsm.maker.user.mapper;

import com.crsm.maker.user.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2018-12-10
 */
@Component
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
