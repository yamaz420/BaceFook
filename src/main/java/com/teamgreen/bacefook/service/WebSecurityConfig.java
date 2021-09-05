package com.teamgreen.bacefook.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private DataSource dataSource;

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
//    auth.inMemoryAuthentication()
//      .withUser("user1").password(passwordEncoder().encode("user1Pass"))
//      .authorities("USER")
//      .and().withUser("admin").password(passwordEncoder().encode("adminPass"))
//      .authorities("ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
//            .antMatchers("/profile/**").hasAnyAuthority("USER", "ADMIN")
//            .antMatchers("/users", "/profile/**").authenticated()
            .antMatchers("/*").permitAll()
//            .anyRequest().authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
//            .loginPage("/login.html")
//            .loginProcessingUrl("/perform_login")
            .usernameParameter("email")
//            .failureUrl("/index.html?error=true")
//            .failureHandler(authenticationFailureHandler())
            .defaultSuccessUrl("/users")
            .permitAll()
            .and()
            .logout()
//            .logoutUrl("/perform_logout")
//            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
//            .logoutSuccessHandler(logoutSuccessHandler())
            .permitAll();
  }


}