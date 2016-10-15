package ru.urfu;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.urfu.user.service.UserDetailsServiceImpl;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class TwitterSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/login", "/", "/register","/css/**", "/js/**").permitAll()
                .antMatchers("/message/**").authenticated()
                .antMatchers("/messages/**").authenticated()
                .and()
                    .authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/messages").failureUrl("/login?error")
                    .usernameParameter("username").passwordParameter("password")
                .and()
                    .logout().permitAll()
                .and()
                    .csrf().disable();
    }
}
