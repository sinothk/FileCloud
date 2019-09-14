package com.sinothk.cloud.comm.exceptions;

import com.sinothk.base.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultData handleException(Exception e){
        e.printStackTrace();
        return ResultData.error(e.getMessage());
    }

    //自定义异常
    @ExceptionHandler(TokenException.class)
    public ResultData handleException(TokenException e){
        e.printStackTrace();
        return ResultData.error(e.getMessage());
    }

//    //这是实体类 参数校验注解不通过会抛出的异常 只有全局异常能拦截到
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ApiResult handleIllegalParamException(MethodArgumentNotValidException e) {
//        e.printStackTrace();
//        List<ObjectError> errors = e.getBindingResult().getAllErrors();
//        String message = "参数不合法";
//        if (errors.size() > 0) {
//            message = errors.get(0).getDefaultMessage();
//        }
//        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,message);
//        return result;
//    }

}
