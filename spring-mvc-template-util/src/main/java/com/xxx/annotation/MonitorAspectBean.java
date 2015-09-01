package com.xxx.annotation;

import com.xxx.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanjuntao
 * @datetime 2015-08-25 14:31
 */

@Aspect
public class MonitorAspectBean implements InitializingBean {

    private static Log logger = LogFactory.getLog(MonitorAspectBean.class);

    private static Map<String, Object> defaultObjMap = new HashMap<String, Object>();

    static {
        defaultObjMap.put("java.util.List", Collections.emptyList());
        defaultObjMap.put("java.util.Map", Collections.emptyMap());
        defaultObjMap.put("java.util.Set", Collections.emptySet());
    }

    private static Map<String, Log> loggerMap = new ConcurrentHashMap<String, Log>();


    private static Object getDefaultObj(Class clazz){
        String className = clazz.getName();
        Object object = defaultObjMap.get(className);
        if(object == null && "void".equals(clazz)){
            try {
                object = BeanUtils.instantiateClass(clazz);
            } catch (BeanInstantiationException ex) {
                object = new Object();
                logger.error("创建MonitorAspectBean返回值对象出现异常",ex);
            }
        }
        return object;
    }

    private String appName;
    private String systemKey;
    private String jvmKey;

    public MonitorAspectBean() {
    }

    @Pointcut("@annotation(com.xxx.annotation.Monitor)")
    public void monitorPoint() {
    }

    private Log getLogger(String className){
        Log logger = loggerMap.get(className);
        if(Utils.isEmpty(logger)){
            logger = LogFactory.getLog(className);
            loggerMap.put(className,logger);
        }
        return logger;
    }

    private void handleLog(String className, String methodName, Throwable ex){
        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
        String fileName = stackTraceElement.getFileName();
        int lineNumber = stackTraceElement.getLineNumber();
        StringBuilder sb = new StringBuilder();
        sb.append("调用");
        sb.append(className).append(".").append(methodName);
        sb.append("[").append(fileName).append(":").append(lineNumber).append("]");
        sb.append("方法出现异常!");
        getLogger(className).error(sb.toString(), ex);
    }

    @Around("monitorPoint()")
    public Object execMonitor(ProceedingJoinPoint pjp) throws Throwable {
        Method method = this.getMethod(pjp);
        String className = pjp.getTarget().getClass().getName();
        String methodName = method.getName();
        Object result = null;
        Object callerInfo = null;
        try {
            Monitor annotation = (Monitor) method.getAnnotation(Monitor.class);
            //annotation一定不为空
            String key = appName + "." + className + "." + methodName;
            //方法调用前监控点 callerInfo
            result = pjp.proceed();
        } catch (Throwable ex) {
            if (callerInfo != null) {
                //方法调用出现异常后监控点
            }
            //处理日志
            handleLog(className,methodName,ex);
            //获取默认返回值
            result = getDefaultObj(method.getReturnType());

        } finally {
            if (callerInfo != null) {
                //方法调用结束后监控点
            }

        }
        return result;
    }

    private Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public void setJvmKey(String jvmKey) {
        this.jvmKey = jvmKey;
    }

    public void afterPropertiesSet() throws Exception {
    }

}
