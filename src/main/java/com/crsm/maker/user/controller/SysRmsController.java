package com.crsm.maker.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.base.ResultStatusCodeEnum;
import com.crsm.maker.user.entity.RoleRms;
import com.crsm.maker.user.entity.SysRms;
import com.crsm.maker.user.entity.SysRole;
import com.crsm.maker.user.entity.SysTree;
import com.crsm.maker.user.service.IRoleRmsService;
import com.crsm.maker.user.service.ISysRmsService;
import com.crsm.maker.user.service.ISysRoleService;
import com.crsm.maker.user.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Slf4j
@RestController
@RequestMapping("user")
public class SysRmsController extends BaseController {

    @Autowired
    private ISysRmsService iSysRmsService;

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private ISysTreeService iSysTreeService;

    @Autowired
    private IRoleRmsService iRoleRmsService;

    @Autowired
    private SessionDAO sessionDAO;

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
        List<SysRole> SysRolelist = iSysRoleService.list(new QueryWrapper<>());
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


    /**
     * 删除权限，（单个
     * @param id
     * @return
     */
    @RequestMapping(value = "delePermission/{id}",method = RequestMethod.GET)
    public String delePermission(@PathVariable("id")Integer id){
        boolean deleTree=iSysTreeService.remove(new UpdateWrapper<SysTree>().eq("rms_id",id).or().eq("f_rms_id",id));
        boolean rmOuterKeyTable=iRoleRmsService.remove(new UpdateWrapper<RoleRms>().eq("rms_id",id));
        boolean delData=iSysRmsService.removeById(id);
        if(deleTree && rmOuterKeyTable && delData){
            return success();
        }
        return fail();
    }

    /**
     * 登出，需要登录后进行
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return success();
    }


    @PostMapping("/login")
    public String login(@RequestParam("userName")String userName,@RequestParam("userPassword")String userPassword){
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return fail(ResultStatusCodeEnum.REPETITION_LOGIN);
        }
        UsernamePasswordToken token=new UsernamePasswordToken(userName,userPassword);
        try {
            subject.login(token);
        }catch(UnknownAccountException e){//未知账户
            throw new UnknownAccountException("未知账户");
        }catch(IncorrectCredentialsException e){//密码错误
            throw new IncorrectCredentialsException("密码错误");
        }catch (LockedAccountException e){//账号已锁定
            throw new LockedAccountException("账号已锁定");
        }catch (ConcurrentAccessException e){//并发访问
            throw new ConcurrentAccessException("账号已登录");
        }catch (AccountException e){
            throw new AccountException("登录失败 未知错误");
        }
        subject.getSession().setAttribute("userInfo",subject.getPrincipals());
        return success(new HashMap<String,Object>(){{
            put("userInfo",subject.getPrincipal());
            put("SessionId",subject.getSession().getId());
        }});
    }

    @RequestMapping("/testSessionDAO")
    public void testSessionDAO(){
        Collection<Session> session=sessionDAO.getActiveSessions();
        Subject subject=SecurityUtils.getSubject();
        System.out.println(subject.getPrincipals());
        session.stream().forEach(s->{
            System.out.println(s.getAttribute("userInfo"));
            System.out.println(s.getStartTimestamp());
            System.out.println(s.getLastAccessTime());
        });
    }


    /**
     * 测试权限
     * @return
     */
    @RequestMapping("testPermission")
    public String testPermission(){
        Subject subject=SecurityUtils.getSubject();
        System.out.println(subject.getSession().getId());
        System.out.println(subject.getSession().getTimeout());
        System.out.println(subject.isPermitted("定时任务:ccr:*"));
        return success();
    }


}
