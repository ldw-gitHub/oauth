/**
 * 
 * @date 2019��11��11��
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
 * �����������ƶ˵�(Token Endpoint)�İ�ȫԼ��
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
		// ʹ���Զ���ͻ����������
		clients.withClientDetails(baseClientDetailService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		DefaultTokenServices tokenServices = new DefaultTokenServices();
		// token�־û�����
		tokenServices.setTokenStore(tokenStore);
		// �ͻ�����Ϣ
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		// �Զ���token����
		tokenServices.setTokenEnhancer(tokenEnhancer());
		// access_token ����Чʱ�� (��), Ĭ�� 12 Сʱ
		tokenServices.setAccessTokenValiditySeconds(60 * 1);
		// refresh_token ����Чʱ�� (��), Ĭ�� 30 ��
		tokenServices.setRefreshTokenValiditySeconds(60 * 2);
		// �Ƿ�֧��refresh_token��Ĭ��false
		tokenServices.setSupportRefreshToken(true);
		// �Ƿ���refresh_token,Ĭ��Ϊtrue(���Ϊfalse,��ÿ������ˢ�¶���ɾ���ɵ�refresh_token,�����µ�refresh_token)
		tokenServices.setReuseRefreshToken(false);

		endpoints.authenticationManager(authenticationManager)
				// refresh_token��ҪuserDetailsService
				.reuseRefreshTokens(false).userDetailsService(baseUserDetailService).tokenServices(tokenServices);
	}

	/**
	 * ��Ȩ�˵㿪��
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				// ����/oauth/token_key��֤�˿���Ȩ�޷���
				.tokenKeyAccess("permitAll()")
				// ����/oauth/check_token��֤�˿���֤Ȩ�޷���
				.checkTokenAccess("permitAll()")
//				.checkTokenAccess("isAuthenticated()")
		        .allowFormAuthenticationForClients();
	}

	/**
	 * @Description �Զ�����������token
	 * @Date 2019/7/9 19:58
	 * @Version 1.0
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new UserTokenEnhancer();
	}

}
