package com.xxx.rpc.http.base;

import com.xxx.util.dto.impl.LinkedHashDto;
import com.xxx.util.dto.Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;
import org.springframework.web.client.support.RestGatewaySupport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author hanjuntao
 */

public class BaseRestService extends RestGatewaySupport {

    protected final Log logger = LogFactory.getLog(getClass());


    protected ObjectMapper objectMapper = new ObjectMapper();

    public BaseRestService() {
    }

    protected String getJSON(String url, Map<String, ?> urlVariables) {
        return getRestTemplate().getForObject(url, String.class, urlVariables);
    }

    protected List<Dto> json2DtoList(String content) {
        List<Dto> result = null;
        try {
            result = objectMapper.readValue(content, new TypeReference<List<LinkedHashDto>>() {
            });
        } catch (IOException e) {
            logger.error("ObjectMapper convert json to Object throw exception.", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    protected String buildUrl(String url, Map<String, ?> urlVariables) {
        String resultUrl = url;
        Assert.notNull(url, "url must not be null.");
        if (!resultUrl.endsWith("?")) {
            resultUrl += "?";
        }
        for (String key : urlVariables.keySet()) {
            resultUrl = resultUrl + (key + "={" + key + "}&");
        }
        return resultUrl;
    }

}
