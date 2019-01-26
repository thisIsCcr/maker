package com.crsm.maker.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crsm.maker.user.entity.SysRms;
import com.crsm.maker.user.mapper.SysRmsMapper;
import com.crsm.maker.user.service.ISysRmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Service
public class SysRmsServiceImpl extends ServiceImpl<SysRmsMapper, SysRms> implements ISysRmsService {

    @Autowired
    private SysRmsMapper sysRmsMapper;

    public List<SysRms> getPermissionDataByFid(int fid) {
        return sysRmsMapper.getPermissionDataByFid(fid);
    }

    @Override
    public int saveAndReturnId(SysRms sysRms) {
        return sysRmsMapper.saveAndReturnId(sysRms);
    }
}
