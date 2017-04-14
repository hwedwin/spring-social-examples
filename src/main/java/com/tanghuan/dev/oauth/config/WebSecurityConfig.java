package com.tanghuan.dev.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by Arthur on 2017/4/14.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置SpringSecurity忽略静态资源
        web.ignoring().antMatchers("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.ico");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().antMatchers("/login.html", "/oauth/**").permitAll()
            .anyRequest().authenticated()
            .and()
                // 配置登录
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/main.html").failureUrl("/login.html?err=true").permitAll()
                // 配置记住我的功能
            .and()
                .rememberMe().rememberMeParameter("rememberMe")
            // 配置登出
            .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll()
            .and()
                .apply(new SpringSocialConfigurer())

            // 配置SESSION管理
            .and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/login.html");

    }
}
