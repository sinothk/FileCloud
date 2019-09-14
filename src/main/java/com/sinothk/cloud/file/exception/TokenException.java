package com.sinothk.cloud.file.exception;

public class TokenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private Integer code;  //错误码

//    public BusinessException() {}
//
//    public BusinessException(ResultEnum resultEnum) {
//        super(resultEnum.getMsg());
//        this.code = resultEnum.getCode();
//    }

    public TokenException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
