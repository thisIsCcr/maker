package com.crsm.maker.base;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Ccr on 2019/1/14
 * JSON消息模板
 **/
public class BaseController {
    private boolean ISSUCCESS = true;
    private boolean ISFAIL = false;

    private String buildMsg(Object data, ResultStatusCodeEnum statusCode, boolean isSuccess) {
        Map jsonObject = new HashMap<String, Object>();
        jsonObject.put("data", data);
        jsonObject.put("status", statusCode.getCode());
        jsonObject.put("isSuccess", isSuccess);
        jsonObject.put("msg", statusCode.getMsg());
        return JSONObject.toJSONString(jsonObject);
    }

    private String buildMsg(ResultStatusCodeEnum statusCode, boolean isSuccess) {
        Map jsonObject = new HashMap<String, Object>();
        jsonObject.put("status", statusCode.getCode());
        jsonObject.put("isSuccess", isSuccess);
        jsonObject.put("msg", statusCode.getMsg());
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     * 操作成功
     *
     * @param data 返回数据
     * @return
     */
    public String success(Object data) {
        return buildMsg(data, ResultStatusCodeEnum.SUCCESS, ISSUCCESS);
    }

    /**
     * 操作成功，不需要返回数据
     *
     * @param
     * @return
     */
    public String success() {
        return buildMsg(ResultStatusCodeEnum.SUCCESS, ISSUCCESS);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public String fail() {
        return buildMsg(ResultStatusCodeEnum.FAIL, ISFAIL);
    }

    /**
     * 操作失败
     *
     * @param statusCode 返回状态编码
     * @return
     */
    public String fail(ResultStatusCodeEnum statusCode) {
        return buildMsg(statusCode, ISFAIL);
    }




}
