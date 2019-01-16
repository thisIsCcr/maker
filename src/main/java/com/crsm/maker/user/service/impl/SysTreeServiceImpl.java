package com.crsm.maker.user.service.impl;

import com.crsm.maker.user.entity.SysTree;
import com.crsm.maker.user.mapper.SysTreeMapper;
import com.crsm.maker.user.service.ISysTreeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限树结构 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Service
public class SysTreeServiceImpl extends ServiceImpl<SysTreeMapper, SysTree> implements ISysTreeService {

    @Autowired
    SysTreeMapper sysTreeMapper;

    @Override
    public List<SysTree> getMenuData() {
        return sysTreeMapper.getMenuData();
    }
}
