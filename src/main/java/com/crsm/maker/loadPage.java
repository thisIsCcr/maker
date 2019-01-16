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
        return "home";
    }

    @RequestMapping("index")
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping("quartzJobList")
    public String getQuratzJobListPage(){
        return "QuartzJobList";
    }

}
