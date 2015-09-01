package com.xxx.util.dto.impl;

import com.xxx.util.dto.Dto;
import com.xxx.util.TypeCastUtils;
import com.xxx.util.Utils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author hanjuntao
 */

public class LinkedHashDto extends LinkedHashMap<String,Object> implements Dto, Serializable{


    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String MESSAGE_KEY = LinkedHashDto.class.getName() + ".MESSAGE";

    public BigDecimal getBigDecimal(String key) {
        Object obj = TypeCastUtils.convert(get(key), "BigDecimal", null);
        if (obj != null)
            return (BigDecimal) obj;
        else
            return null;
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getDate(String key) {
        Object obj = TypeCastUtils.convert(get(key), "Date", "yyyy-MM-dd");
        if (obj != null)
            return (Date) obj;
        else
            return null;
    }

    /**
     * 以Integer类型返回键值
     *
     * @param key
     *            键名
     * @return Integer 键值
     */
    public Integer getInteger(String key) {
        Object obj = TypeCastUtils.convert(get(key), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return null;
    }

    /**
     * 以Long类型返回键值
     *
     * @param key
     *            键名
     * @return Long 键值
     */
    public Long getLong(String key) {
        Object obj = TypeCastUtils.convert(get(key), "Long", null);
        if (obj != null)
            return (Long) obj;
        else
            return null;
    }

    /**
     * 以String类型返回键值
     *
     * @param key
     *            键名
     * @return String 键值
     */
    public String getString(String key) {
        Object obj = TypeCastUtils.convert(get(key), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return "";
    }

    /**
     * 以List类型返回键值
     *
     * @param key
     *            键名
     * @return List 键值
     */
    public List getList(String key){
        return (List)get(key);
    }

    /**
     * 以Timestamp类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Timestamp getTimestamp(String key) {
        Object obj = TypeCastUtils.convert(get(key), "Timestamp", TIMESTAMP_FORMAT );
        if (obj != null)
            return (Timestamp) obj;
        else
            return null;
    }

    /**
     * 以Boolean类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Boolean getBoolean(String key){
        Object obj = TypeCastUtils.convert(get(key), "Boolean", null);
        if (obj != null)
            return (Boolean) obj;
        else
            return null;
    }

    public String toJson() {
        return Utils.toJSON(this);
    }

    public Dto toDto(String json) {
        Dto result = this;
        try {
            result = new ObjectMapper().readValue(json,this.getClass());
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 设置提示信息
     */
    public void setMsg(String pMsg){
        put(MESSAGE_KEY, pMsg);
    }

    /**
     * 获取提示信息
     *
     */
    public String getMsg(){
        return getString(MESSAGE_KEY);
    }

}
