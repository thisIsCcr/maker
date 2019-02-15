package com.crsm.maker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created  by Ccr on 2019/1/14
 **/
@Controller
@RequestMapping
public class loadPage {

    @RequestMapping("/")
    public String enterLoginPage(){
        return "base/home";
    }

    @RequestMapping("index")
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping("quartzPage")
    public String getQuratzJobListPage(){
        return "quartzJobList";
    }

    @RequestMapping("permission")
    public String getPermissionPage(){
        return "permission";
    }

    @RequestMapping("fileResource")
    public String getresourceFilePage(){
        return "fileResource";
    }
}
