/**
 * 
 * @date 2019��11��11��
 */
package com.framework.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.framework.service.BaseUserDetailService;

/**
 * һ�仰���ܼ���
 * 
 * @author liudawei
 */

@Configuration
@EnableWebSecurity
@Order(-1)
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// �Զ�ע��UserDetailsService
	@Autowired
	private BaseUserDetailService baseUserDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();

		http
				// ʹ��form��post��ʽ���е�¼
				.formLogin()
				// ��¼ҳ��Ϊ�Զ���ĵ�¼ҳ��
				.loginPage("/login")
				// ���õ�¼�ɹ���תҳ�棬error=true����ҳ�������Ϣ��չʾ
				.successForwardUrl("/index").failureUrl("/login?error=true").permitAll().and()
				// ������½�Ϳ��Է��ʵķ���������ö��ŷָ�
				.authorizeRequests().antMatchers("/oauth/**").permitAll()
//				.antMatchers("/api/**").permitAll()
				// ��������Ҫ��Ȩ�����
				.anyRequest().authenticated();
		// session����,ʧЧ����ת
		http.sessionManagement().invalidSessionUrl("/login");
		// ���û���¼�������һ����¼�ˣ�ͬһ���û��������ط���¼��ǰһ���޳�����
		// http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
		// ���û���¼�������һ����¼�ˣ�ͬһ���û��������ط����ܵ�¼
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
		// �˳�ʱ���cookies
		http.logout().deleteCookies("JESSIONID");
		// ���������������
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		//
		http.addFilterBefore(filter, CsrfFilter.class);
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// �����̬��Դ�����ص�����
		web.ignoring().antMatchers("/static/**");
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
