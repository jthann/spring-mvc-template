package com.xxx.rpc.dubbo;


import com.xxx.domain.SliderVO;

import java.util.List;

/**
 * @author hanjuntao
 */
public interface IndexDubboService {

    List<SliderVO> getSliderBanner(String ctxPath);

}
