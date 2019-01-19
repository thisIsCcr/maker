package com.crsm.maker.user.controller;


import com.crsm.maker.base.BaseController;
import com.crsm.maker.user.service.ISysRmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@RestController
@RequestMapping("/rms")
public class SysRmsController extends BaseController {

    @Autowired
    private ISysRmsService iSysRmsService;

    @RequestMapping("getAllrmsData")
    public String getAllrmsData(@RequestParam("fid")Integer fid){
        return success(iSysRmsService.getPermissionDataByFid(fid));
    }
}
