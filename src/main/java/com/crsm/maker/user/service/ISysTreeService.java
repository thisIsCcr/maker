package com.crsm.maker.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crsm.maker.user.entity.SysTree;

import java.util.List;

/**
 * <p>
 * 权限树结构 服务类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
public interface ISysTreeService extends IService<SysTree> {

    public List<SysTree> getMenuData();

}
