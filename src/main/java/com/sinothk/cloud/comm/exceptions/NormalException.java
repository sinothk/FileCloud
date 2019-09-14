package com.sinothk.cloud.comm.exceptions;

import com.sinothk.base.keyValue.ErrorCode;

public class NormalException extends RuntimeException {

    private int code;  //错误码
    private String msg;

    public NormalException(int code) {
        this.code = code;
        msg = ErrorCode.getErrorMsg(code);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
