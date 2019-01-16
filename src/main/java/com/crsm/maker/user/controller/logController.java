package com.crsm.maker.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.user.entity.SysTree;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.service.ISysTreeService;
import com.crsm.maker.user.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * creat by Ccr on 2018/12/4
 **/
@RestController
public class logController extends BaseController {

    @Autowired
    ISysUserService iSysUserService;

    @Autowired
    ISysTreeService iSysTreeService;

    @RequestMapping("/getAllUser")
    public Object getAllUser(){
        List<SysUser> sysUser = iSysUserService.list(new QueryWrapper<SysUser>());
        return sysUser;
    }

    @RequestMapping("/getMenuData")
    public JSONObject getMenuData(){
        Subject currentSubject=SecurityUtils.getSubject();
        System.out.println("是否登录："+currentSubject.isAuthenticated());
        List<SysTree> list=iSysTreeService.getMenuData();
        return success(list);
    }
}
