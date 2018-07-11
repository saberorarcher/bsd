package com.mos.bsd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description:security验证
 * @author:张世豪
 * @date:2018年5月3日 下午1:03:29
 * @version:1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 基于内存的用户存储(暂时不需要用到,用的是配置文件中的用户)
	 */
	/*
	 * @Override public void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().withUser("admin").password("mosFashion").roles(
	 * "admin"); }
	 */

	/**
	 * 请求拦截
	 * 
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		// 配置需要登录拦截(多个地址可用逗号分隔开)
				antMatchers("/swagger-ui.html").authenticated()
				// .antMatchers(HttpMethod.POST,"/order").authenticated()
				// 通过登录验证
				.and().httpBasic();
		// 禁用csrf
		http.csrf().disable();
	}

}
