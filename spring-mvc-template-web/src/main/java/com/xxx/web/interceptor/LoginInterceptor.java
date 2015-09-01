package com.xxx.web.interceptor;

import com.xxx.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hanjuntao
 * @datetime 2015-07-24 18:39
 */

public class LoginInterceptor extends HandlerInterceptorAdapter{

    private Log logger = LogFactory.getLog(LoginInterceptor.class);

    public static final String USER_PIN = "userPin";
    public static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    private String loginUrl;

    private String anonymousCartCookieName;

    public void setAnonymousCartCookieName(String anonymousCartCookieName){
        this.anonymousCartCookieName = anonymousCartCookieName;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxValue = request.getHeader("X-Requested-With");
        return Utils.isNotEmpty(ajaxValue) && AJAX_HEADER_VALUE.equalsIgnoreCase(ajaxValue);
    }

    public void handlePassportLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.isAjaxRequest(request)) {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().print("{\"error\":\"NotLogin\"}");
            response.getWriter().flush();
        } else {
            response.sendRedirect(loginUrl);
        }
    }

    private Object parseCookie(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        Object e = null;
        try {
            e = this.parseCookie(request, response);
        } catch (Throwable t) {
            logger.error("解析登陆Cookie信息出现异常！", t);
        }
        this.handlePassportLogin(request, response);
        return false;
    }

}
