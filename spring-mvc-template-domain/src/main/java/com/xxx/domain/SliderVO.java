package com.xxx.domain;

import java.io.Serializable;

/**
 * @author hanjuntao
 * @datetime 2015-08-31 14:18
 */
public class SliderVO implements Serializable {

    private String imgUrl;
    private String caption;
    private String hrefPath;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getHrefPath() {
        return hrefPath;
    }

    public void setHrefPath(String hrefPath) {
        this.hrefPath = hrefPath;
    }
}
