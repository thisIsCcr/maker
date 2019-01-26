package com.crsm.maker.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crsm.maker.user.entity.SysRms;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
public interface ISysRmsService extends IService<SysRms> {

    public List<SysRms> getPermissionDataByFid(int fid);

    public int saveAndReturnId(SysRms sysRms);

}
