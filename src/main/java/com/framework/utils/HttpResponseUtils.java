package com.framework.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.model.BaseResponse;

/**
 * @Description: Description
 * @ProjectName: spring-parent
 * @Package: com.yaomy.common.utils.HttpUtils
 * @Date: 2019/7/18 9:34
 * @Version: 1.0
 */
public class HttpResponseUtils {
    /**
     * �쳣���������
     */
    public static void writeError(BaseResponse bs, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(bs.getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), bs);
    }
    /**
     * SUCESS���������
     */
    public static void writeSuccess(BaseResponse bs, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(bs.getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), bs);
    }
}
