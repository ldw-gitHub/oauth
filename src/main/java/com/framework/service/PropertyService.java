package com.framework.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @Description: ��ȡ�����ļ�������������
 * @Version: 1.0
 */
@Service
public class PropertyService {
    @Autowired
    private Environment env;
    /**
     * @Description ��ȡ�����ļ���ָ��������ֵ
     * @Version  1.0
     */
    public String getProperty(String property){
        return env.getProperty(property);
    }
    /**
     * @Description ��ȡ�����ļ������ԣ����Ϊ�գ���ΪĬ��ֵ
     * @Version  1.0
     */
    public String getProperty(String property, String defaultValue){
        String val = env.getProperty(property);
        if(StringUtils.isEmpty(val)){
            return  defaultValue;
        }
        return val;
    }
    /**
     * @Description ��ȡ�����ļ���ָ��������ֵ
     * @Version  1.0
     */
    public <T> T getProperty(String property, Class<T> clazz){
        return env.getProperty(property, clazz);
    }
    /**
     * @Description ��ȡ�����ļ���ָ�������ԣ�����Ϊnull,�򷵻�Ĭ��defaultValue
     * @Version  1.0
     */
    public <T> T getProperty(String property, T defaultValue, Class<T> clazz){
        T val = env.getProperty(property, clazz);
        if(val == null){
            return defaultValue;
        }
        return val;
    }
    /**
     * @Description �ж������ļ����Ƿ����ָ����keyֵ
     * @Version  1.0
     */
    public boolean containsProperty(String property){
        return env.containsProperty(property);
    }
}
