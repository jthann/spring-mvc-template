package com.xxx.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prevent the XSS attack.
 *
 * @author hanjuntao
 * @datetime 2015-07-17 09:02
 */

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    /**
     * Regex pattern which is illegal.
     */
    private static final Pattern PATTERN = Pattern.compile("[<>,\\*\\?\\+\r\n\"\']+");

    private List<String> paramNameList;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CollectionUtils.isEmpty(paramNameList)) {
            return true;
        }
        String paramValue = null;
        for(String paramName : paramNameList){
            paramValue = request.getParameter(paramName);
            if(StringUtils.isNotBlank(paramValue)){
                if(!isSafe(paramValue)){
                    request.getRequestDispatcher("/WEB-INF/vm/error/error.vm").forward(request, response);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Judge whether the value is safe.
     *
     * @param paramValue
     * @return
     */
    private boolean isSafe(String paramValue) {
        Matcher m = PATTERN.matcher(paramValue);
        return !m.find();
    }

    public void setParamNameList(List<String> paramNameList) {
        this.paramNameList = paramNameList;
    }
}
