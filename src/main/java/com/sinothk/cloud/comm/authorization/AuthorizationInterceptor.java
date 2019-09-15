package com.sinothk.cloud.comm.authorization;

import com.sinothk.base.keyValue.ErrorCode;
import com.sinothk.base.utils.TokenUtil;
import com.sinothk.cloud.comm.exceptions.NormalException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("authorizationInterceptor")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(TokenUtil.createToken(TokenUtil.EXPIRE_TIME_15D, "liangyt"));

        //设置跨域--开始
        HttpServletResponse httpResponse = response;
        HttpServletRequest httpRequest = request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest, httpResponse);
            return true;
        }
        //设置跨域--结束

        TokenCheck annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(TokenCheck.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (token == null || token.equals("")) {
            token = request.getParameter("token");
        }

        //token为空
        if (token == null || token.equals("")) {
            throw new NormalException(ErrorCode.TOKEN_NOT_EMPTY); //ErrorCode.TOKEN_NOT_EMPTY
        }

        //查询token信息
//        TokenEntity tokenEntity = tokenService.getByToken(token);
//        if (tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()) {
//            throw new RenException(ErrorCode.TOKEN_INVALID);
//        }

        if (TokenUtil.checkToken(token) != null) {
            //设置userId到request里，后续根据userId，获取用户信息
            request.setAttribute("userName", TokenUtil.getUserName(token));
            return true;
        } else {
            throw new NormalException(ErrorCode.TOKEN_ERROR);//
        }
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
    }

}