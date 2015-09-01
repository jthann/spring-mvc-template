package com.xxx.web.base;

import com.xxx.annotation.Monitor;
import com.xxx.util.Utils;
import com.xxx.util.dto.Dto;
import com.xxx.util.dto.ResultDto;
import com.xxx.util.dto.impl.LinkedHashDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hanjuntao
 */
public class BaseAction {

    protected final Log logger = LogFactory.getLog(getClass());



    @Value("${page.size.default}")
    protected int defaultPageSize = 10;
    @Value("${passport.checkLogin.loginUrl}")
    protected String loginUrl;
    @Value("${ept-id-passport.passport_auth_cookie_name}")
    protected String cookieName;
    @Value("${passport.updatePwd.url}")
    protected String updatePwdUrl;


    public BaseAction() {
    }

    protected void setCache(HttpServletResponse response, int cdnCacheTime){
        String cacheTimeStr = String.valueOf(cdnCacheTime * 60);
        response.addHeader("cache-control","public,max-age="+cacheTimeStr);
        response.addHeader("Vary","Accept-Encoding");
        response.addDateHeader("Last-Modified", System.currentTimeMillis());
    }
    @Monitor
    protected void sendJson(HttpServletResponse response, String json) {
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().print(json);
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("Could not write Json data to response.", e);
        }

    }

    protected void sendJson(HttpServletResponse response, Object object) {
        String json = "";
        if(object instanceof ResultDto){
            json = ((ResultDto) object).toJSON();
        }else {
            json = Utils.toJSON(object);
        }
        sendJson(response,json);
    }

    protected void sendHtml(HttpServletResponse response, String html) {
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(html);
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("Could not write html data to response", e);
        }
    }

    public String redirect(String location) {
        return "redirect:"+location;
    }

    public String notFound(HttpServletRequest request, Model model, String errorLog){
        String refererUrl = request.getHeader("referer");
        model.addAttribute("errorLog",errorLog);
        model.addAttribute("referUrl",refererUrl);
        return "error/systemError";
    }


    protected Dto getParamDto(HttpServletRequest request) {
        Dto paramDto = new LinkedHashDto();
        Map<String,String[]> paramMap = request.getParameterMap();
        for(String paramKey : paramMap.keySet()) {
            String[] valueArray = paramMap.get(paramKey);
            paramDto.put(paramKey,valueArray[0]);
        }
        return paramDto;
    }


    protected List<String> parseIdList(String ids){
        List<String> idList = new ArrayList<String>();
        if(Utils.isNotEmpty(ids)){
            String[] idArray = ids.trim().split("\\|");
            for(String id : idArray){
                if(Utils.isNotEmpty(id.trim())){
                    idList.add(id);
                }
            }
        }
        return idList;
    }
}
