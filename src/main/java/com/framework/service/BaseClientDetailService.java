/**
 * 
 * @date 2019年11月11日
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
 * 使用内存或者JDBC来实现客户端详情服务
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
		// 这里可以改为查询数据库
		if ("client_password".equals(clientId)) {
			log.info(clientId);
			client = new BaseClientDetails();
			client.setClientId(propertyService.getProperty("spring.security.oauth.resource.client.id"));
			String secret = passwordEncoder().encode(propertyService.getProperty("spring.security.oauth.resource.client.secret"));
			System.out.println("secret ================================================= " + secret);
			client.setClientSecret(secret);
			/**
			 * Authorization Code：用验证获取code，再用code去获取token（用的最多的方式，也是最安全的方式）
			 * Implicit: 隐式授权模式 Client Credentials (用來取得 App Access Token)
			 * Resource Owner Password Credentials
			 */
			client.setAuthorizedGrantTypes(
					Arrays.asList("authorization_code", "client_credentials", "refresh_token", "password", "implicit"));
			// 不同的client可以通过 一个scope 对应 权限集
			client.setScope(Arrays.asList("all", "select"));
			client.setAuthorities(AuthorityUtils.createAuthorityList("admin_role"));
			client.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
			client.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
			
			//注册回调地址
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
