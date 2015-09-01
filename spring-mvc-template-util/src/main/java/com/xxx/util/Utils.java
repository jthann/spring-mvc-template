package com.xxx.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * @author hanjuntao
 * @datetime 2015-07-16 17:24
 */
public class Utils {

    public static Log logger = LogFactory.getLog(Utils.class);

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param obj 待检查对象
     * @return boolean 返回的布尔值
     */

    public static final String CURRENCY = "IDR";

    public static final int COUNTRY_ID = 62;

    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static int DEFAULT_PAGE_SIZE = 10;

    public final static int DEFAULT_PAGE = 1;

    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj == "")
            return true;
        if (obj instanceof String) {
            if (((String) obj).length() == 0) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param obj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj == null)
            return false;
        if (obj == "")
            return false;
        if (obj instanceof String) {
            if (((String) obj).length() == 0) {
                return false;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                return false;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    public static String toJSON(Object obj) {
        String json = "";
        StringWriter writer = new StringWriter();
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
            ObjectMapper mapper = new ObjectMapper();
            //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            mapper.setDateFormat(DEFAULT_DATE_FORMAT);
            mapper.writeValue(jsonGenerator, obj);
            json = writer.toString();
        } catch (Exception e) {
            logger.error("对象转换JSO字符串N出错!", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return json;
    }

    public static <T> T parseObject(String jsonString, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(DEFAULT_DATE_FORMAT);
        T result = null;
        try {
            result = mapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            logger.error("JSON转换对象出错!", e);
        }
        return result;
    }

    public static <T> T parseObject(String jsonString, TypeReference typeReference) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(DEFAULT_DATE_FORMAT);
        T result = null;
        try {
            result = mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            logger.error("JSON转换对象出错!", e);
        }
        return result;
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode error.", e);
            return null;
        }
    }
}
