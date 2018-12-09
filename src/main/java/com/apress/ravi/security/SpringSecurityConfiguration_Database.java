package com.apress.ravi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.apress.ravi.service.UserInfoDetailsService;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration_Database extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserInfoDetailsService userInfoDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userInfoDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.formLogin().loginPage("/login/login.html").and()
		http.httpBasic().realmName("User Registration System")
		.and().authorizeRequests().antMatchers("/login/login.html", "/templates/home.html", "/").permitAll().anyRequest().authenticated()
		.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				//.and().authorizeRequests().antMatchers("/api/user/**").authenticated()
//				.and().httpBasic().realmName("User Registration System")
//				.and().csrf().disable();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and().authorizeRequests().antMatchers("/api/user/**").authenticated()
//				//.and().authorizeRequests().antMatchers("/h2-console/**").authenticated()
//				.and().httpBasic().realmName("User Registration System").and().csrf().disable();
//	}
}