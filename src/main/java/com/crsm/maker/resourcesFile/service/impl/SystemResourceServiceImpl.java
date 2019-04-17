package com.crsm.maker.resourcesFile.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.mapper.SystemResourceMapper;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
@Service
public class SystemResourceServiceImpl extends ServiceImpl<SystemResourceMapper, SysResource> implements ISystemResourceService {

    @Autowired
    SystemResourceMapper systemResourceMapper;

    @Override
    public IPage<SysResource> selectPageVo(Page<SysResource> page, SysResource sysResource) {
        return systemResourceMapper.selectPageVo(page, sysResource);
    }

    @Override
    public Integer saveAndReturnId(SysResource sysResource) {
        return systemResourceMapper.saveAndReturnId(sysResource);
    }



    @Override
    public List<SysResource> getAllMusicInfo(Wrapper wrapper) {
        return systemResourceMapper.getAllMusicInfo(wrapper);

    }


}
