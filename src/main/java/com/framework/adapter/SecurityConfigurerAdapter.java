/**
 * 
 * @date 2019年11月11日
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
 * 一句话功能简述
 * 
 * @author liudawei
 */

@Configuration
@EnableWebSecurity
@Order(-1)
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// 自动注入UserDetailsService
	@Autowired
	private BaseUserDetailService baseUserDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();

		http
				// 使用form表单post方式进行登录
				.formLogin()
				// 登录页面为自定义的登录页面
				.loginPage("/login")
				// 设置登录成功跳转页面，error=true控制页面错误信息的展示
				.successForwardUrl("/index").failureUrl("/login?error=true").permitAll().and()
				// 允许不登陆就可以访问的方法，多个用逗号分隔
				.authorizeRequests().antMatchers("/oauth/**").permitAll()
//				.antMatchers("/api/**").permitAll()
				// 其他的需要授权后访问
				.anyRequest().authenticated();
		// session管理,失效后跳转
		http.sessionManagement().invalidSessionUrl("/login");
		// 单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
		// http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
		// 单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
		// 退出时情况cookies
		http.logout().deleteCookies("JESSIONID");
		// 解决中文乱码问题
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		//
		http.addFilterBefore(filter, CsrfFilter.class);
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 解决静态资源被拦截的问题
		web.ignoring().antMatchers("/static/**");
	}

	/**
	 * 用户验证
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 加入自定义的安全认证
		auth.userDetailsService(baseUserDetailService).passwordEncoder(passwordEncoder());
	}

	/**
	 * @Description 自定义加密
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
	 * 设置userDetailsService
	 * provider.setUserDetailsService(baseUserDetailService); // 禁止隐藏用户未找到异常
	 * provider.setHideUserNotFoundExceptions(false); // 使用BCrypt进行密码的hash
	 * provider.setPasswordEncoder(new BCryptPasswordEncoder(6)); return
	 * provider; }
	 */

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * @Description OAuth2 token持久化接口
	 * @Date 2019/7/9 17:45
	 * @Version 1.0
	 */
	@Bean
	public TokenStore tokenStore() {
		// token保存在内存中（也可以保存在数据库、Redis中）。
		// 如果保存在中间件（数据库、Redis），那么资源服务器与认证服务器可以不在同一个工程中。
		// 注意：如果不保存access_token，则没法通过access_token取得用户信息
		// return new InMemoryTokenStore();
		return new InMemoryTokenStore();
	}
}
