package com.zqzess.dlms.config;
/**
 *
 * @Author zqzess
 * @Date 2021/10/27
 * @File CrossInterceptor.java
 * @Package com.zqzess.dlms.config
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮,虽九死其尤未毁
 *
 **/

//import org.springframework.http.HttpHeaders;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//class CrossInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        if (request.getHeader(HttpHeaders.ORIGIN) != null) {
//            String origin = request.getHeader("Origin");
//            response.addHeader("Access-Control-Allow-Origin", origin);
//            response.addHeader("Access-Control-Allow-Credentials", "true");
//            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT,PATCH, HEAD");
//            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
//            response.addHeader("Access-Control-Max-Age", "3600");
//        }
//        return true;
//    }
//}
