//package com.vinove.service.impl;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.vinove.entity.CustomEmployeeDetail;
//import com.vinove.entity.Employee;
//import com.vinove.repository.EmployeeRepository;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//	@Autowired
//	private EmployeeRepository employeeRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<Employee> user = employeeRepository.findByEmail(email);
//		user.orElseThrow(() -> new UsernameNotFoundException("employee not found"));
//		return user.map(CustomEmployeeDetail::new).get();
//	}
//
//}