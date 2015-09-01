package com.xxx.util.dto;

/**
 * @author hanjuntao
 */
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object Interface
 *
 * @author hanjuntao
 */
public interface Dto extends Map<String,Object> {


    /**
     * 以Integer类型返回键值
     *
     * @param key 键名
     * @return Integer 键值
     */
    public Integer getInteger(String key);


    public Long getLong(String key);


    public String getString(String key);


    public BigDecimal getBigDecimal(String key);


    public Date getDate(String key);


    public List getList(String key);


    public Timestamp getTimestamp(String pStr);


    public Boolean getBoolean(String key);


    public String toJson();


    public void setMsg(String msg);

    /**
     * 获取提示信息
     */
    public String getMsg();

}
