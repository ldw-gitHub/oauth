package com.framework.nums;

/**
 * @Description: �Զ���״̬���쳣ö����
 * @ProjectName: spring-parent
 * @Package: com.yaomy.common.HttpStatusMsg
 * @Date: 2019/7/18 15:09
 * @Version: 1.0
 */
public enum HttpStatusMsg {
    OK(200,"SUCCESS"),
    UNKNOW_EXCEPTION(201, "δ֪�쳣"),
    RUNTIME_EXCEPTION(202, "����ʱ�쳣"),
    NULL_POINTER_EXCEPTION(203, "��ָ���쳣"),
    CLASS_CAST_EXCEPTION(204, "����ת���쳣"),
    IO_EXCEPTION(205, "IO�쳣"),
    INDEX_OUTOF_BOUNDS_EXCEPTION(206, "����Խ���쳣"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTIION(207, "�������Ͳ�ƥ��"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(208, "ȱ�ٲ���"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(209, "��֧�ֵ�method����"),

    //--------------------OAuth2��֤�쳣------------------
    AUTHENTICATION_EXCEPTION(300, "��¼�쳣�������¼��Ϣ..."),
    ACCESS_DENIDED_EXCEPTION(301, "������Դ����"),
    PASSWORD_EXCEPTION(302, "�����쳣"),
    USERNAME_EXCEPTION(303, "�û����쳣");

    private final int status;
    private final String message;

    private HttpStatusMsg(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
