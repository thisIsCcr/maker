package com.crsm.maker.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.user.entity.RoleRms;
import com.crsm.maker.user.entity.SysRms;
import com.crsm.maker.user.entity.SysRole;
import com.crsm.maker.user.entity.SysTree;
import com.crsm.maker.user.service.IRoleRmsService;
import com.crsm.maker.user.service.ISysRmsService;
import com.crsm.maker.user.service.ISysRoleService;
import com.crsm.maker.user.service.ISysTreeService;
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

    @Autowired
    private ISysTreeService iSysTreeService;

    @Autowired
    private IRoleRmsService iRoleRmsService;

    /**
     * 获取所有菜单
     *
     * @param fid
     * @return
     */
    @RequestMapping("getAllrmsData")
    public String getAllrmsData(@RequestParam("fid") Integer fid) {
        return success(iSysRmsService.getPermissionDataByFid(fid));
    }

    /**
     * 根据id获取角色
     *
     * @return
     */
    @RequestMapping(value = "queryByidQuery/{id}", method = RequestMethod.GET)
    public String queryByidQuery(@PathVariable("id") Integer id) {
        List<SysRole> SysRolelist = iSysRoleService.list(new QueryWrapper<SysRole>().eq("id", id));
        return success(SysRolelist);
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @RequestMapping(value = "getAllrole", method = RequestMethod.GET)
    public String getAllrole() {
        List<SysRole> SysRolelist = iSysRoleService.list(new QueryWrapper<SysRole>());
        return success(SysRolelist);
    }

    /**
     * 添加权限
     *
     * @param rmsName
     * @param rmsIocn
     * @param type
     * @param roleId
     * @param fRmsId
     * @param rmsUrl
     */
    @RequestMapping(value = "addPermission", method = RequestMethod.POST)
    public String addPermission(@RequestParam("rmsName") String rmsName,
                                @RequestParam("rmsUrl") String rmsUrl,
                                @RequestParam("rmsIocn") String rmsIocn,
                                @RequestParam("type") Integer type,
                                @RequestParam("fRmsId") Integer fRmsId,
                                @RequestParam("roleId") Integer roleId) {
        SysRms sysRms = new SysRms();
        sysRms.setRmsIocn(rmsIocn);
        sysRms.setRmsName(rmsName);
        sysRms.setRmsUrl(rmsUrl);
        iSysRmsService.saveAndReturnId(sysRms);
        //添加层级关系
        SysTree sysTree = new SysTree();
        sysTree.setFRmsId(fRmsId);
        sysTree.setRmsId(sysRms.getId());
        sysTree.setType(type);
        iSysTreeService.save(sysTree);
        //添加角色关系
        RoleRms roleRms = new RoleRms();
        roleRms.setRmsId(sysRms.getId());
        roleRms.setRoleId(roleId);
        iRoleRmsService.save(roleRms);
        return success();
    }


}
