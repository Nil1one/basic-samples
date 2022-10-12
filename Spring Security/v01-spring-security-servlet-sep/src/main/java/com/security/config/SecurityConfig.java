package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;


@Configuration
public class SecurityConfig {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    @Lazy
    @Resource(name = "v1UserDetailsService")
    UserDetailsService v1UserDetailsService;

    @Lazy
    @Resource(name = "adminUserDetailsService")
    UserDetailsService adminUserDetailsService;

    @Bean
    public UserDetailsService v1UserDetailsService() {
        return (username) -> User.withUsername(username).password("{noop}123").roles("V1").build();
    }

    @Bean
    public UserDetailsService adminUserDetailsService() {
        return (username) -> User.withUsername(username).password("{noop}123").roles("ADMIN").build();
    }

    @Bean
    public SecurityFilterChain v1SecurityFilterChain(HttpSecurity http) throws Exception {
        daoAuthenticationProvider.setUserDetailsService(v1UserDetailsService);
        // 注意: 角色配置时不需要添加`ROLE_`前缀，因为会自动补全
        return http
                .csrf().disable()
                .antMatcher("/v1/**")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic().and()
                .authenticationManager(new ProviderManager(daoAuthenticationProvider))
                .authorizeRequests((customer) -> {
                    customer.antMatchers("/v1/login/username", "/v1/login/phone_number").permitAll();
                    customer.anyRequest().hasAnyRole("V1");
                }).build();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        daoAuthenticationProvider.setUserDetailsService(adminUserDetailsService);
        // 注意: 角色配置时不需要添加`ROLE_`前缀，因为会自动补全
        return http
                .csrf().disable()
                .antMatcher("/admin/**")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic().and()
                .authenticationManager(new ProviderManager(daoAuthenticationProvider))
                .authorizeRequests((customer) -> {
                    customer.antMatchers("/admin/login/username", "/admin/login/phone_number").permitAll();
                    customer.anyRequest().hasAnyRole("ADMIN");
                }).build();
    }
}
