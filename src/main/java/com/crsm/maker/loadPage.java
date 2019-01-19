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

    @RequestMapping("quartzJobList")
    public String getQuratzJobListPage(){
        return "QuartzJobList";
    }

    @RequestMapping("permission")
    public String getPermissionPage(){
        return "permission";
    }
}
