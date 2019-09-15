package com.sinothk.cloud.comm.exceptions;

import com.sinothk.base.entity.ResultData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import java.util.Map;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultData handleException(Exception e) {
        e.printStackTrace();
        return ResultData.error("12121212");//e.getMessage()
    }

    //自定义异常
    @ExceptionHandler(NormalException.class)
    public ResultData handleException(NormalException e) {
        e.printStackTrace();
        return ResultData.error(e.getCode(), e.getMsg());
    }

    /**
     * 处理上传异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public String handleAll(Throwable e) {
        e.printStackTrace();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json;charset=UTF-8");
        return headers.toString();

//        return ResultData.error(20000, e.getMessage());
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type","application/json;charset=UTF-8");

//        return new ResponseEntity<>(new Result(-1,"上传文件异常！",null),headers, HttpStatus.OK);
    }

    /* spring默认上传大小1MB 超出大小捕获异常MaxUploadSizeExceededException */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResultData handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResultData.error("文件大小超出1MB限制, 请压缩或降低文件质量! ");
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
