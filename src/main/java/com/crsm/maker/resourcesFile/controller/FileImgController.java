package com.crsm.maker.resourcesFile.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-03-27
 */
@RestController
@RequestMapping("/fileImage")
public class FileImgController extends BaseController {

    @Autowired
    public ISystemResourceService iSystemResourceService;


    @GetMapping("/getCoverImageInfo")
    public String getCoverImageInfo(){
        List  list=iSystemResourceService.list(new QueryWrapper<SysResource>().eq("res_type","2"));
        return success(list);
    }

}
