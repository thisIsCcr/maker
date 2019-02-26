package com.crsm.maker.base;

import lombok.Getter;

/**
 * Created  by Ccr on 2019/2/20
 **/
public enum ResultStatusCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(500,"失败"),
    REPEAT_DATA_ERROR(401,"数据重复");

    @Getter
    private int code;

    @Getter
    private String msg;

    ResultStatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
