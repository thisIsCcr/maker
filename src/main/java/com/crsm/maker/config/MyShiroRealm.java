package com.crsm.maker.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.user.entity.SysTree;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.mapper.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * creat by Ccr on 2018/12/10
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleRmsMapper roleRmsMapper;

    @Autowired
    SysTreeMapper sysTreeMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //通过用户名查询角色Id
        List roleId = roleMapper.getRoleId(user.getId());
        if (!roleId.isEmpty()) {
            Set<Permission> wildcars = new HashSet<>();
            List<SysTree> permissions = sysTreeMapper.getPermission(roleId);
            //添加角色
            info.addRoles(roleId);
            //添加权限
            if (!permissions.isEmpty()) {
                for (SysTree item : permissions) {
                    wildcars.add(new WildcardPermission(buildPermissionformatData(item.getRmsId().toString(), item.getSysRms().getRmsUrl(), item.getSysRms().getId().toString())));
                }
                info.addObjectPermissions(wildcars);
            }
            //开放所有权限
            info.addObjectPermission(new WildcardPermission(buildPermissionformatData("*", "*", "*")));
            for (Permission lis : info.getObjectPermissions()) {
                System.out.println(lis);
            }
        }
        return info;
    }

    public static String buildPermissionformatData(String... content) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(content[0]);
        stringBuffer.append(":");
        stringBuffer.append(content[1]);
        stringBuffer.append(":");
        stringBuffer.append(content[2]);
        return stringBuffer.toString();
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

    /**
     * 密码加密格式
     *
     * @param args
     */
    public static void main(String[] args) {
        String algorithmName = "md5";
        String username = "ccr";
        String password = "123456";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
        System.out.println(salt2);
    }
}
