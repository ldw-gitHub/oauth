/**
 * 
 * @date 2019��11��7��
 */
package com.framework.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ��֤�û�����ģ��
 * @author ldw
 */
public class SysUserAuthentication implements UserDetails {

    private static final long serialVersionUID = 2678080792987564753L;

    /**
     * ID��
     */
    private String uuid;
    /**
     * �û���
     */
    private String username;
    /**
     * ����
     */
    private String password;
    /**
     * �˻���Ч
     */
    private boolean accountNonExpired;
    /**
     * �˻�����
     */
    private boolean accountNonLocked;
    /**
     * ƾ֤��Ч
     */
    private boolean credentialsNonExpired;
    /**
     * ����״̬
     */
    private boolean enabled;
    /**
     * Ȩ���б�
     */
    private Collection<GrantedAuthority>  authorities;
    /**
     * ID��
     * @return uuid 
     */
    public String getUuid() {
        return uuid;
    }
    
    /**
     * ID��
     * @param uuid ID��
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    /**
     * �û���
     * @return username 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * �û���
     * @param username �û���
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * ����
     * @return password 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * ����
     * @param password ����
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * �˻���Ч
     * @return accountNonExpired 
     */
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    /**
     * �˻���Ч
     * @param accountNonExpired �˻���Ч
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    /**
     * �˻�����
     * @return accountNonLocked 
     */
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    /**
     * �˻�����
     * @param accountNonLocked �˻�����
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    /**
     * ƾ֤��Ч
     * @return credentialsNonExpired 
     */
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    /**
     * ƾ֤��Ч
     * @param credentialsNonExpired ƾ֤��Ч
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    /**
     * ����״̬
     * @return enabled 
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * ����״̬
     * @param enabled ����״̬
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Ȩ���б�
     * @return authorities 
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    /**
     * Ȩ���б�
     * @param authorities Ȩ���б�
     */
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
}
