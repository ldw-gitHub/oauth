package com.framework.adapter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.framework.model.BaseResponse;
import com.framework.nums.HttpStatusMsg;
import com.framework.utils.HttpResponseUtils;

/**
 * @Description: 用来解决认证过的用户访问无权限资源时的异常
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.handler.AjaxAccessDeniedHandler
 * @Date: 2019/7/1 15:34
 * @Version: 1.0
 */
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        HttpResponseUtils.writeError(BaseResponse.createResponse(HttpStatusMsg.ACCESS_DENIDED_EXCEPTION.getStatus(), HttpStatusMsg.ACCESS_DENIDED_EXCEPTION.getMessage()+","+e.toString()), response);
    }
}
