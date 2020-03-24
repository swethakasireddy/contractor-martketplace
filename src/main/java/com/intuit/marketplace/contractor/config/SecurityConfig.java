/**
 * @author swethakasireddy
 * This class is for Security configuration for the Swagger API documentation
 */
package com.intuit.marketplace.contractor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/projects/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/projects").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/projects/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/projects/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
