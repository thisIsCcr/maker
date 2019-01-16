package com.crsm.maker.base;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created  by Ccr on 2019/1/14
 **/
@Data
public class BaseController {
    private Integer status=200;
    private boolean isSuccess=true;

    public JSONObject success(Object msg){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",msg);
        jsonObject.put("status",status);
        jsonObject.put("isSuccess",isSuccess);
        return jsonObject;
    }
}
