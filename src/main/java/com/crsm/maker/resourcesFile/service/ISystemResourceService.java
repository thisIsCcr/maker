package com.crsm.maker.resourcesFile.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crsm.maker.resourcesFile.entity.SysResource;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
public interface ISystemResourceService extends IService<SysResource> {

    public IPage<SysResource> selectPageVo(Page<SysResource> page, SysResource sysResource);

    Integer saveAndReturnId(SysResource sysResource);


}
