/**
 * 
 * @date 2019年11月7日
 */
package com.framework.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.framework.adapter.handler.UserAccessDeniedHandler;
import com.framework.adapter.handler.UserAuthenticationEntryPoint;
import com.framework.service.PropertyService;

/**
 * OAuth 资源服务器配置
 * 
 * @author ldw
 */
@Configuration
@EnableResourceServer
@Order(3)
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
				// 资源ID
				.resourceId(propertyService.getProperty("spring.security.oauth.resource.id"))
				// 用来解决匿名用户访问无权限资源时的异常
				.authenticationEntryPoint(userAuthenticationEntryPoint)
				// 访问资源权限相关异常处理
				.accessDeniedHandler(userAccessDeniedHandler);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		       .antMatchers("/getSms").authenticated()
//		       .anyRequest().permitAll()// 其他没有限定的请求，允许访问
//			   .and().anonymous()// 对于没有配置权限的其他请求允许匿名访问
//			   .and().formLogin()// 使用 spring security 默认登录页面
//		       .and().httpBasic();

		
		// 对 api/ 请求进行拦截
//	     http.authorizeRequests().antMatchers("/api/**").authenticated();
	     
	     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		     .and().authorizeRequests()
		     .antMatchers("/api/**").authenticated();//配置api访问控制，必须认证过后才可以访问



	}

	/**
	 * @Description 令牌服务
	 * @Date 2019/7/15 18:07
	 * @Version 1.0
	 */
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		return defaultTokenServices;
	}

}