package com.framework.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.framework.nums.HttpStatusMsg;

import lombok.Data;

/**
 * @Description: Description
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.po.AjaxResponseBody
 * @Date: 2019/7/1 15:33
 * @Version: 1.0
 */
@Data
public class BaseResponse implements Serializable {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    /**
     * @Description ������Ӧ����
     * @Date 2019/7/18 10:10
     * @Version  1.0
     */
    public static BaseResponse createResponse(int status, String message){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(status);
        baseResponse.setMessage(message);
        return baseResponse;
    }
    /**
     * @Description ������Ӧ����
     * @Date 2019/7/18 10:10
     * @Version  1.0
     */
    public static BaseResponse createResponse(HttpStatusMsg httpStatusMsg){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(httpStatusMsg.getStatus());
        baseResponse.setMessage(httpStatusMsg.getMessage());
        return baseResponse;
    }
    /**
     * @Description ������Ӧ����
     * @Date 2019/7/18 10:10
     * @Version  1.0
     */
    public static BaseResponse createResponse(int status, String message, Object data){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(status);
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        return baseResponse;
    }
    /**
     * @Description ������Ӧ����
     * @Date 2019/7/18 10:10
     * @Version  1.0
     */
    public static BaseResponse createResponse(HttpStatusMsg httpStatusMsg, Object data){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(httpStatusMsg.getStatus());
        baseResponse.setMessage(httpStatusMsg.getMessage());
        baseResponse.setData(data);
        return baseResponse;
    }
}
