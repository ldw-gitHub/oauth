package com.framework.adapter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.framework.nums.HttpStatusMsg;
import com.framework.utils.HttpResponseUtils;

/**
 * @Description: ������������û�������Ȩ����Դʱ���쳣
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.handler.AjaxAuthenticationEntryPoint
 * @Date: 2019/7/1 15:36
 * @Version: 1.0
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        HttpResponseUtils.writeError(com.framework.model.BaseResponse.createResponse(HttpStatusMsg.AUTHENTICATION_EXCEPTION.getStatus(), HttpStatusMsg.AUTHENTICATION_EXCEPTION.getMessage()+","+e.toString()), response);

    }
}
