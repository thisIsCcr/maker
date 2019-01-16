package com.crsm.maker.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.mapper.SysUserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * creat by Ccr on 2018/12/10
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Object principl = authenticationToken.getPrincipal();
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("usr_name", (String) principl));
        //未知账户
        if (sysUser == null) {
            throw new UnknownAccountException();
        }
        //账户已被锁定
        if (Boolean.TRUE.equals(Boolean.valueOf(sysUser.getIsFreeze().intValue() == 1))) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, sysUser.getUsrPassword(), ByteSource.Util.bytes(sysUser.getCredentialsSalt()), getName());
        return info;
    }

    public static void main(String[] args) {
        String algorithmName = "md5";
        String username = "ccr";
        String password = "123456";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(algorithmName, password,salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
        System.out.println(salt2);
    }
}
