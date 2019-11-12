/**
 * 
 * @date 2019��11��11��
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
 * һ�仰���ܼ���
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
		
//		//��ѯ�û���Ϣ
//		   // System.out.println(auth);
//        List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("admin_role"); //��ν�Ľ�ɫ��ֻ������ROLE_ǰ׺
//        
//        //��������user�����׳����������õ��쳣
//		User user = new User(username,"123456",true,true,true,true,list);
//		//user = new User(username, "{noop}123456", list);
//		log.info("---------------------------------------------");
//		log.info(JSON.toJSONString(user));
//		log.info("---------------------------------------------");
		
		 SysUserAuthentication user = null; 
	        if("admin".equals(username)) {
	           // IntegrationAuthentication auth = IntegrationAuthenticationContext.get();
	            //�������ͨ��auth ��ȡ user ֵ
	            //Ȼ����ݵ�ǰ��¼��ʽtype Ȼ�󴴽�һ��sysuserauthentication �������� username �� password
	            //����ʹ���ֻ���֤���¼�ģ� username�����ֻ��� password����6λ����֤��{noop}000000
	           // System.out.println(auth);
	            List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("admin_role"); //��ν�Ľ�ɫ��ֻ������ROLE_ǰ׺
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
	            //��������user�����׳����������õ��쳣
	        }
		return user;
	}

}
