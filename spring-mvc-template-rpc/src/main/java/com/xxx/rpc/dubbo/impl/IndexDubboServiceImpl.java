package com.xxx.rpc.dubbo.impl;

import com.xxx.domain.SliderVO;
import com.xxx.rpc.dubbo.IndexDubboService;
import com.xxx.rpc.dubbo.base.BaseDubboService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanjuntao
 */
@Service("indexDubboService")
public class IndexDubboServiceImpl extends BaseDubboService implements IndexDubboService {

    //@Resource
    //private IndexService indexService;

    @Override
    public List<SliderVO> getSliderBanner(String ctxPath) {
        return new ArrayList<SliderVO>();
    }
}
