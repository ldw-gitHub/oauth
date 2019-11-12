/**
 * 
 * @date 2019年11月11日
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
 * 一句话功能简述
 * 
 * @author liudawei
 */

@Configuration
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// 自动注入UserDetailsService
	@Autowired
	private BaseUserDetailService baseUserDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				// 静态资源控制,不进行拦截,permitAll容许用户访问
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/loginPage").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.antMatchers("/static/**").permitAll()
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/loginPage").failureUrl("/loginPage?code=").permitAll()
				// 登出页
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
				// 其余所有请求全部需要鉴权认证
				.and().authorizeRequests().anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
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
