/**
 * 
 * @date 2019��11��7��
 *//*
package com.framework.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.framework.adapter.handler.UserAccessDeniedHandler;
import com.framework.adapter.handler.UserAuthenticationEntryPoint;
import com.framework.service.PropertyService;

*//**
 * OAuth ��Դ����������
 * 
 * @author ldw
 *//*
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	@Autowired
	private UserAccessDeniedHandler userAccessDeniedHandler;

	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private PropertyService propertyService;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenServices(tokenServices())
				// ��ԴID
				.resourceId(propertyService.getProperty("spring.security.oauth.resource.id"))
				// ������������û�������Ȩ����Դʱ���쳣
				.authenticationEntryPoint(userAuthenticationEntryPoint)
				// ������ԴȨ������쳣����
				.accessDeniedHandler(userAccessDeniedHandler);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		System.out.println("==========================ResourceServerConfigurerAdapter======================");
		http.authorizeRequests().antMatchers("/oauth2/**", "/oauth/**","/loginPage","/login").permitAll().anyRequest()
				.authenticated();
	}

	*//**
	 * @Description ���Ʒ���
	 * @Date 2019/7/15 18:07
	 * @Version 1.0
	 *//*
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		return defaultTokenServices;
	}

}*/