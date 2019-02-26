package com.crsm.maker.base;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Ccr on 2019/1/14
 **/
public class BaseController {
    private Integer SUCCESS = 200;
    private Integer FAIL = 500;
    private boolean ISSUCCESS = true;
    private boolean ISFAIL= false;

    public String success(Object msg) {
        Map jsonObject = new HashMap<String, Object>();
        jsonObject.put("data", msg);
        jsonObject.put("status", SUCCESS);
        jsonObject.put("isSuccess", ISSUCCESS);
        return JSONObject.toJSONString(jsonObject);
    }

    public String success() {
        Map jsonObject = new HashMap<String, Object>();
        jsonObject.put("status", SUCCESS);
        jsonObject.put("isSuccess", ISSUCCESS);
        return JSONObject.toJSONString(jsonObject);
    }

    public String fail(){
        Map jsonObject = new HashMap<String, Object>();
        jsonObject.put("status", FAIL);
        jsonObject.put("isSuccess", ISFAIL);
        return JSONObject.toJSONString(jsonObject);
    }
}
