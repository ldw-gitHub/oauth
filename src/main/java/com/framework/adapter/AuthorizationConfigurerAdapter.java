/**
 * 
 * @date 2019年11月11日
 */
package com.framework.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.framework.model.UserTokenEnhancer;
import com.framework.service.BaseClientDetailService;
import com.framework.service.BaseUserDetailService;

/**
 * 用来配置令牌端点(Token Endpoint)的安全约束
 * 
 * @author liudawei
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	BaseUserDetailService baseUserDetailService;
	@Autowired
	BaseClientDetailService baseClientDetailService;
	@Autowired
	private TokenStore tokenStore;
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 使用自定义客户端详情服务
		clients.withClientDetails(baseClientDetailService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		DefaultTokenServices tokenServices = new DefaultTokenServices();
		// token持久化容器
		tokenServices.setTokenStore(tokenStore);
		// 客户端信息
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		// 自定义token生成
		tokenServices.setTokenEnhancer(tokenEnhancer());
		// access_token 的有效时长 (秒), 默认 12 小时
		tokenServices.setAccessTokenValiditySeconds(60 * 1);
		// refresh_token 的有效时长 (秒), 默认 30 天
		tokenServices.setRefreshTokenValiditySeconds(60 * 2);
		// 是否支持refresh_token，默认false
		tokenServices.setSupportRefreshToken(true);
		// 是否复用refresh_token,默认为true(如果为false,则每次请求刷新都会删除旧的refresh_token,创建新的refresh_token)
		tokenServices.setReuseRefreshToken(false);

		endpoints.authenticationManager(authenticationManager)
				// refresh_token需要userDetailsService
				.reuseRefreshTokens(false).userDetailsService(baseUserDetailService).tokenServices(tokenServices);
	}

	/**
	 * 授权端点开放
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				// 开启/oauth/token_key验证端口无权限访问
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
				.checkTokenAccess("permitAll()")
//				.checkTokenAccess("isAuthenticated()")
		        .allowFormAuthenticationForClients();
	}

	/**
	 * @Description 自定义生成令牌token
	 * @Date 2019/7/9 19:58
	 * @Version 1.0
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new UserTokenEnhancer();
	}

}
