package com.xxx.rpc.http.impl;

import com.xxx.rpc.http.IndexRestService;
import com.xxx.rpc.http.base.BaseRestService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author hanjuntao
 */
@Service("indexRestService")
public class IndexRestServiceImpl extends BaseRestService implements IndexRestService {

    private static final String REST_URL = "";

    private String getModuleHtml(String urlPath, Map<String, String> urlVariables) {
        return getRestTemplate().getForObject(REST_URL + urlPath, String.class, urlVariables);
    }

    @Override
    public String getSliderBannerModule() {
        return null;
    }
}
