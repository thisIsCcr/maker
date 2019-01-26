package com.crsm.maker.base;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Ccr on 2019/1/14
 **/
@Data
public class BaseController {
    private Integer status=200;
    private boolean isSuccess=true;

    public String success(Object msg){
        Map jsonObject=new HashMap<String,Object>();
        jsonObject.put("data",msg);
        jsonObject.put("status",status);
        jsonObject.put("isSuccess",isSuccess);
        return JSONObject.toJSONString(jsonObject);
    }

    public String success(){
        Map jsonObject=new HashMap<String,Object>();
        jsonObject.put("status",status);
        jsonObject.put("isSuccess",isSuccess);
        return JSONObject.toJSONString(jsonObject);
    }
}
