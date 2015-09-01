package com.xxx.util.dto;

import com.xxx.util.Utils;
import com.xxx.util.dto.impl.LinkedHashDto;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装客户端服务器端传输数据
 *
 * @author hanjuntao
 * @datetime 2015-07-16 14:55
 */
public class ResultDto extends LinkedHashDto {
    private static final long serialVersionUID = -5473316155223284286L;
    private boolean success;
    private String msgTitle;
    private String message;
    private String errorCode;//错误码


    private List<Dto> dataList = new ArrayList<Dto>();

    public static ResultDto newInstance() {
        return new ResultDto();
    }

    public static ResultDto newInstance(String json) {
        ResultDto result = newInstance();
        try {
            result = new ObjectMapper().readValue(json, ResultDto.class);
        } catch (IOException e) {
        }
        return result;
    }

    public ResultDto() {
        this.success = true;
        this.msgTitle = "";
        this.message = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean isSuccess) {
        this.success = isSuccess;
    }

    public String getMsgTitle() {

        return msgTitle;
    }

    public List<Dto> getDataList() {
        if (Utils.isEmpty(dataList)) {
            return (List<Dto>) this.get("dataList");
        }
        return this.dataList;
    }

    public void setMsgTitle(String msgTitle) {

        this.msgTitle = msgTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String toJSON() {
        this.put("success", success);
        if (Utils.isNotEmpty(msgTitle)) {
            this.put("msgTitle", msgTitle);
        }
        if (Utils.isNotEmpty(message)) {
            this.put("message", message);
        }
        return Utils.toJSON(this);
    }

    public String toString() {
        return toJSON();
    }
}
