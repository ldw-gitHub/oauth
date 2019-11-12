/**
 * 
 * @date 2019��11��11��
 */
package com.framework.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.framework.service.BaseUserDetailService;

/**
 * һ�仰���ܼ���
 * 
 * @author liudawei
 */

@Configuration
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// �Զ�ע��UserDetailsService
	@Autowired
	private BaseUserDetailService baseUserDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				// ��̬��Դ����,����������,permitAll�����û�����
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/loginPage").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.antMatchers("/static/**").permitAll()
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/loginPage").failureUrl("/loginPage?code=").permitAll()
				// �ǳ�ҳ
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
				// ������������ȫ����Ҫ��Ȩ��֤
				.and().authorizeRequests().anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	/**
	 * �û���֤
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// �����Զ���İ�ȫ��֤
		auth.userDetailsService(baseUserDetailService).passwordEncoder(passwordEncoder());
	}

	/**
	 * @Description �Զ������
	 * @Date 2019/7/10 15:07
	 * @Version 1.0
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean public DaoAuthenticationProvider daoAuthenticationProvider(){
	 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //
	 * ����userDetailsService
	 * provider.setUserDetailsService(baseUserDetailService); // ��ֹ�����û�δ�ҵ��쳣
	 * provider.setHideUserNotFoundExceptions(false); // ʹ��BCrypt���������hash
	 * provider.setPasswordEncoder(new BCryptPasswordEncoder(6)); return
	 * provider; }
	 */

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * @Description OAuth2 token�־û��ӿ�
	 * @Date 2019/7/9 17:45
	 * @Version 1.0
	 */
	@Bean
	public TokenStore tokenStore() {
		// token�������ڴ��У�Ҳ���Ա��������ݿ⡢Redis�У���
		// ����������м�������ݿ⡢Redis������ô��Դ����������֤���������Բ���ͬһ�������С�
		// ע�⣺���������access_token����û��ͨ��access_tokenȡ���û���Ϣ
		// return new InMemoryTokenStore();
		return new InMemoryTokenStore();
	}
}
