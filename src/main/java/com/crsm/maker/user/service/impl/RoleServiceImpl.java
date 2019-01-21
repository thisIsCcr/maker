package com.crsm.maker.user.service.impl;

import com.crsm.maker.user.entity.Role;
import com.crsm.maker.user.mapper.RoleMapper;
import com.crsm.maker.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
