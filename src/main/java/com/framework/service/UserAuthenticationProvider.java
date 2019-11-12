package com.framework.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: �û��Զ��������֤
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.provider.MyAuthenticationProvider
 * @Date: 2019/7/2 17:17
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	BaseUserDetailService baseUserDetailService;
    
	/**
	 * @Description �Զ������
	 * @Date 2019/7/10 15:07
	 * @Version 1.0
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    //@Autowired
   // private ApplicationEventPublisher publisher;
    /**
     * @Description ��֤��������һ��Authentication��ʵ�����������֤�ɹ�������null�������֤ʧ��
     * @Date 2019/7/5 15:19
     * @Version  1.0
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        log.info("username == " + username + "  password == " + password);
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("username�û���������Ϊ��");
        }
        if(StringUtils.isBlank(password)){
            throw new BadCredentialsException("���벻����Ϊ��");
        }
        //��ȡ�û���Ϣ
        UserDetails user = baseUserDetailService.loadUserByUsername(username);
        //�Ƚ�ǰ�˴�����������ĺ����ݿ��м��ܵ������Ƿ����
        if (!passwordEncoder().matches(password, user.getPassword())) {
            //�������벻��ȷ�¼�
           // publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new BadCredentialsException("password���벻��ȷ");
        }
        //��ȡ�û�Ȩ����Ϣ
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }
    /**
     * @Description �����AuthenticationProvider֧�ִ����Authentication�����򷵻�true
     * @Date 2019/7/5 15:18
     * @Version  1.0
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
