package com.miniproject.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${cors.allowed-origin}")
	private String allowedOrigin;

	// Injected here because spring does not manage filter bean registration.
	@Autowired
	private SecurityFilter securityFilter;
	
	@Autowired
	private AdminFilter adminFilter;
	
	/* 
	 * overrides spring default /login authentication.
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().and()
        	.authorizeRequests().antMatchers("/**").permitAll().and().headers().frameOptions().disable();
    }
	
	@Bean
	public FilterRegistrationBean<SecurityFilter> SecurityFilterRegistration() {
	    FilterRegistrationBean<SecurityFilter> registration = new  FilterRegistrationBean<SecurityFilter>();
	    registration.setFilter(securityFilter);
	    registration.addUrlPatterns("/user/*");
	    registration.addUrlPatterns("/order/*");
	    return registration;
	}
	
	@Bean
	public FilterRegistrationBean<AdminFilter> AdminFilterRegistration() {
	    FilterRegistrationBean<AdminFilter> registration = new  FilterRegistrationBean<AdminFilter>();
	    registration.setFilter(adminFilter);
	    registration.addUrlPatterns("/item/*");
	    return registration;
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigin));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
}
