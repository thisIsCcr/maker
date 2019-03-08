package com.crsm.maker.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crsm.maker.user.entity.SysRms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Component
@Mapper
public interface SysRmsMapper extends BaseMapper<SysRms> {

     List<SysRms> getPermissionDataByFid(@Param("fid")int fid);

     int saveAndReturnId(SysRms sysRms);

     @Deprecated
     List<SysRms> getPermission(@Param("roleId")List roleId);
}
