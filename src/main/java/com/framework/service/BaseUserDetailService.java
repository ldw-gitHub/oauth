/**
 * 
 * @date 2019年11月11日
 */
package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.framework.model.SysUserAuthentication;

import lombok.extern.slf4j.Slf4j;

/**
 * 一句话功能简述
 * @author liudawei
 */
@Component
@Slf4j
public class BaseUserDetailService implements UserDetailsService{
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		//查询用户信息
//		   // System.out.println(auth);
//        List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("admin_role"); //所谓的角色，只是增加ROLE_前缀
//        
//        //这里会根据user属性抛出锁定，禁用等异常
//		User user = new User(username,"123456",true,true,true,true,list);
//		//user = new User(username, "{noop}123456", list);
//		log.info("---------------------------------------------");
//		log.info(JSON.toJSONString(user));
//		log.info("---------------------------------------------");
		
		 SysUserAuthentication user = null; 
	        if("admin".equals(username)) {
	           // IntegrationAuthentication auth = IntegrationAuthenticationContext.get();
	            //这里可以通过auth 获取 user 值
	            //然后根据当前登录方式type 然后创建一个sysuserauthentication 重新设置 username 和 password
	            //比如使用手机验证码登录的， username就是手机号 password就是6位的验证码{noop}000000
	           // System.out.println(auth);
	            List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("admin_role"); //所谓的角色，只是增加ROLE_前缀
	            user = new SysUserAuthentication();
	            user.setUsername(username);
	            user.setPassword(passwordEncoder().encode("123456"));
	            user.setAuthorities(list);
	            user.setAccountNonExpired(true);
	            user.setAccountNonLocked(true);
	            user.setCredentialsNonExpired(true);
	            user.setEnabled(true);
	            
	            //user = new User(username, "{noop}123456", list);
	            log.info("---------------------------------------------");
	            log.info(JSON.toJSONString(user));
	            log.info("---------------------------------------------");
	            //这里会根据user属性抛出锁定，禁用等异常
	        }
		return user;
	}

}
