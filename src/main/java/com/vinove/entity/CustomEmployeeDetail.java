//package com.vinove.entity;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class CustomEmployeeDetail extends Employee implements UserDetails {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = -4151309700543050222L;
//
//	public CustomEmployeeDetail(Employee user) {
//		super(user);
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorityList = new ArrayList<>();
//		authorityList.add(new SimpleGrantedAuthority(super.getRole().getName()));
//		return authorityList;
//	}
//
//	@Override
//	public String getPassword() {
//
//		return super.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//
//		return super.getEmail();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//
//		return true;
//	}
//
//}
