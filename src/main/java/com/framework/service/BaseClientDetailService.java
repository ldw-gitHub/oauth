/**
 * 
 * @date 2019��11��11��
 */
package com.framework.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * ʹ���ڴ����JDBC��ʵ�ֿͻ����������
 * 
 * @author liudawei
 */
@Component
@Slf4j
public class BaseClientDetailService implements ClientDetailsService {
	
	@Autowired
	private PropertyService propertyService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		System.out.println("clientId ================================================= " + clientId);
		BaseClientDetails client = null;
		// ������Ը�Ϊ��ѯ���ݿ�
		if ("client_password".equals(clientId)) {
			log.info(clientId);
			client = new BaseClientDetails();
			client.setClientId(propertyService.getProperty("spring.security.oauth.resource.client.id"));
			String secret = passwordEncoder().encode(propertyService.getProperty("spring.security.oauth.resource.client.secret"));
			System.out.println("secret ================================================= " + secret);
			client.setClientSecret(secret);
			/**
			 * Authorization Code������֤��ȡcode������codeȥ��ȡtoken���õ����ķ�ʽ��Ҳ���ȫ�ķ�ʽ��
			 * Implicit: ��ʽ��Ȩģʽ Client Credentials (�Á�ȡ�� App Access Token)
			 * Resource Owner Password Credentials
			 */
			client.setAuthorizedGrantTypes(
					Arrays.asList("authorization_code", "client_credentials", "refresh_token", "password", "implicit"));
			// ��ͬ��client����ͨ�� һ��scope ��Ӧ Ȩ�޼�
			client.setScope(Arrays.asList("all", "select"));
			client.setAuthorities(AuthorityUtils.createAuthorityList("admin_role"));
			client.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1��
			client.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1��
			
			//ע��ص���ַ
			Set<String> uris = new HashSet<>();
			// uris.add("http://localhost/login");
			uris.add("http://www.baidu.com");
			client.setRegisteredRedirectUri(uris);
		}
		if (client == null) {
			throw new NoSuchClientException("No client width requested id: " + clientId);
		}
		return client;
	}

}
