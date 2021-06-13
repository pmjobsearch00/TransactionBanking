package com.tenx.sample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Class WebSecurityConfig
 * 
 * It is the security configuration class for the Web 
 * 
 * @author PM
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.tenx.sample")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Allow all resource security free access as no login is created yet
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.authorizeRequests().antMatchers("/css/**", "*/**").permitAll(); 
    }
     
}
