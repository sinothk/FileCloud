package com.sinothk.cloud.comm.exceptions;

import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.file.config.ServerConfig;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource(name = "serverConfig")
    private ServerConfig serverConfig;

    @ExceptionHandler(Exception.class)
    public ResultData handleException(Exception e) {
        e.printStackTrace();
        return ResultData.error(e.getMessage());//
    }

    //自定义异常
    @ExceptionHandler(NormalException.class)
    public ResultData handleException(NormalException e) {
        e.printStackTrace();
        return ResultData.error(e.getCode(), e.getMsg());
    }

    /* spring默认上传大小，超出大小捕获异常MaxUploadSizeExceededException */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResultData handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResultData.error("文件大小超出" + serverConfig.getFileMaxSize() + "限制");
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
