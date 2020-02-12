package com.toyota.rentalcar.dev.commons.config;

import com.toyota.rentalcar.dev.commons.utils.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppWebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final DataSource   dataSource;
    private final UsersService usersService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/rental-car/**").permitAll()
                    .antMatchers("/manager/**").hasRole("MANAGER")
                    .antMatchers("/admin/**").hasRole("ADMIN");
        http
                .formLogin()
                .loginPage("/login");
        http
                .exceptionHandling().accessDeniedPage("/accessDenied");
        http
                .logout().logoutUrl("/logout").invalidateHttpSession(true);
        http
                .rememberMe()
                .key("toyota-rentalcar")
                .userDetailsService(usersService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60*60*24);
    }

    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder());
    }
}
