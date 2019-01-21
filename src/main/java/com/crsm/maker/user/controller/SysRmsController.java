package com.crsm.maker.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.user.entity.SysRole;
import com.crsm.maker.user.service.ISysRmsService;
import com.crsm.maker.user.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@RestController
@RequestMapping("rms")
public class SysRmsController extends BaseController {

    @Autowired
    private ISysRmsService iSysRmsService;

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 获取所有菜单
     * @param fid
     * @return
     */
    @RequestMapping("getAllrmsData")
    public String getAllrmsData(@RequestParam("fid")Integer fid){
        return success(iSysRmsService.getPermissionDataByFid(fid));
    }

    /**
     * 根据id获取角色
     * @return
     */
    @RequestMapping(value = "queryByidQuery/{id}",method = RequestMethod.GET)
    public String queryByidQuery(@PathVariable("id")Integer id){
        List<SysRole> SysRolelist=iSysRoleService.list(new QueryWrapper<SysRole>().eq("id",id));
        return success(SysRolelist);
    }

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping(value = "getAllrole",method = RequestMethod.GET)
    public String getAllrole(){
        List<SysRole> SysRolelist=iSysRoleService.list(new QueryWrapper<SysRole>());
        return success(SysRolelist);
    }


}
