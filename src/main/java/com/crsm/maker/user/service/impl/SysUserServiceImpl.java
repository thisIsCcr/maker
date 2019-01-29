package com.crsm.maker.user.service.impl;

import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.mapper.SysUserMapper;
import com.crsm.maker.user.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2018-12-10
 */
@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
