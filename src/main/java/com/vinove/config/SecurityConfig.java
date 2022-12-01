//package com.vinove.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import com.vinove.service.impl.CustomUserDetailService;
//
//@Configuration
////@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	CustomUserDetailService customUserDetailService;
//
//	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	public void configure(WebSecurity web) {
//
//		try {
//			web.ignoring().antMatchers("/resources/**");
//		} catch (Exception e) {
//			logger.info("you are not signIn");
//		}
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) {
//		try {
//			auth.userDetailsService(customUserDetailService);
//		} catch (Exception e) {
//			logger.info("user not available");
//		}
//	}
//
//	/// configure method...
//
//	@Override
//	protected void configure(HttpSecurity http) {
//		try {
//			http.
//			// spring security
//					authorizeRequests().antMatchers("/**").permitAll()
////					.antMatchers("/employee/**", "/manager/**", "/department/**").hasRole("ADMIN")
////					.antMatchers("/employee/department/**").hasRole("MANAGER")
//					.anyRequest().authenticated().and()
//					.formLogin().loginPage("/login").permitAll().failureUrl("/login?error= true")
//					.defaultSuccessUrl("/shop").usernameParameter("email").passwordParameter("password").and().logout()
//					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
//					.invalidateHttpSession(true).deleteCookies("JSESSIONID").and().exceptionHandling()
//					.accessDeniedPage("/403").and().csrf().disable();
//		} catch (Exception e) {
//			logger.info("user configuration security exception");
//		}
//	}
//}
