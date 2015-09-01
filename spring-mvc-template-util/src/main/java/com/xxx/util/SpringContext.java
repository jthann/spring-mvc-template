package com.xxx.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author hanjuntao
 * @datetime 2015-08-10 20:12
 */
public class SpringContext implements ApplicationContextAware {

    private static Log logger = LogFactory.getLog(SpringContext.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContext.applicationContext = applicationContext;

    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException{
        T bean = null;
        try {
            bean = applicationContext.getBean(name,requiredType);
        } catch (BeansException e){
            logger.error("调用SpringContext获取名称为"+name+"的bean失败！");
        }
        return bean;
    }
}
