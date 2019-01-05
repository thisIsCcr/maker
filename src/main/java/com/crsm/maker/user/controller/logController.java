package com.crsm.maker.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * creat by Ccr on 2018/12/4
 **/
@CrossOrigin
@Controller
public class logController {

    @Autowired
    ISysUserService iSysUserService;

    @RequestMapping({"/unauth","hi"})
    public String hello() {
        return "home";
    }

    @ResponseBody
    @RequestMapping("/getAllUser")
    public Object getAllUser(){
        List<SysUser> sysUser = iSysUserService.list(new QueryWrapper<SysUser>());
        return sysUser;
    }

    @RequestMapping("/")
    public String enterLoginPage(){
        return "home";
    }

    @RequestMapping("index")
    public String getIndexPage(){
        return "index";
    }

}
