package com.framework.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @Description: �û��Զ���token���ƣ�����access_token��refresh_token
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.oauth2.enhancer.UserTokenEnhancer
 * @Date: 2019/7/9 19:43
 * @Version: 1.0
 */
public class UserTokenEnhancer implements TokenEnhancer {
    /**
     * @Description ���¶�������token
     * @Date 2019/7/9 19:56
     * @Version  1.0
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
       if(accessToken instanceof DefaultOAuth2AccessToken){
           DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
           token.setValue(getToken());
           //ʹ��DefaultExpiringOAuth2RefreshToken������refresh_token���Դ�����ʱ�䣬������Ч��refresh_tokenһֱ��Ч
           DefaultExpiringOAuth2RefreshToken refreshToken = (DefaultExpiringOAuth2RefreshToken)token.getRefreshToken();
           //OAuth2RefreshToken refreshToken = token.getRefreshToken();
           if(refreshToken instanceof DefaultExpiringOAuth2RefreshToken){
               token.setRefreshToken(new DefaultExpiringOAuth2RefreshToken(getToken(), refreshToken.getExpiration()));
           }
           Map<String, Object> additionalInformation = new HashMap<String,Object>();
           additionalInformation.put("client_id", authentication.getOAuth2Request().getClientId());
           //��Ӷ���������Ϣ
           token.setAdditionalInformation(additionalInformation);
           return token;
       }
        return accessToken;
    }
    /**
     * @Description �����Զ���token
     * @Date 2019/7/9 19:50
     * @Version  1.0
     */
    private String getToken(){
        return StringUtils.join(UUID.randomUUID().toString().replace("-", ""));
    }
}
