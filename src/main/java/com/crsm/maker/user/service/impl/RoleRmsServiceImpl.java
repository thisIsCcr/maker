package com.crsm.maker.user.service.impl;

import com.crsm.maker.user.entity.RoleRms;
import com.crsm.maker.user.mapper.RoleRmsMapper;
import com.crsm.maker.user.service.IRoleRmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2019-01-25
 */
@Transactional
@Service
public class RoleRmsServiceImpl extends ServiceImpl<RoleRmsMapper, RoleRms> implements IRoleRmsService {

}
