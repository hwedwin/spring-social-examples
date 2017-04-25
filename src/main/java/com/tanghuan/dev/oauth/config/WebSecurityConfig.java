package com.tanghuan.dev.oauth.config;

import com.tanghuan.dev.oauth.security.entrypoint.HttpForbiddenEntryPoint;
import com.tanghuan.dev.oauth.security.uds.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
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
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login.html", "/oauth/**", "/sign**").permitAll()
            .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpForbiddenEntryPoint(), AnyRequestMatcher.INSTANCE)
            .and()
                .logout()
                .logoutUrl("/logout.html")
                .clearAuthentication(true)
                .logoutSuccessUrl("/login.html")
            .and()
                .apply(new SpringSocialConfigurer());

    }

}
