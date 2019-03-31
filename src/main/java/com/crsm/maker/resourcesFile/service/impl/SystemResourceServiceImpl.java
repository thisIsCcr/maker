package com.crsm.maker.resourcesFile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    public List<SysResource> getAllMusicInfo() {
        LambdaQueryWrapper wrapper=Wrappers.<SysResource>lambdaQuery()
                .notExists("SELECT audio_lrc_id FROM file_audio fa WHERE fa.`audio_lrc_id`=sr.`id`")
                .likeLeft(SysResource::getResName, ".lrc");
        return systemResourceMapper.getAllMusicInfo(wrapper);

    }


}
