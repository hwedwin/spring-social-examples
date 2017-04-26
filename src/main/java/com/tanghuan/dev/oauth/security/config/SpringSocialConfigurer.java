package com.tanghuan.dev.oauth.security.config;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.*;

/**
 * Created by Arthur on 2017/4/26.
 */
public class SpringSocialConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private UserIdSource userIdSource;

    private String postLoginUrl;

    private String postFailureUrl;

    private String signupUrl;

    private String connectionAddedRedirectUrl;

    private String defaultFailureUrl;

    private boolean alwaysUsePostLoginUrl = false;

    public SpringSocialConfigurer() {
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        UsersConnectionRepository usersConnectionRepository = getDependency(applicationContext, UsersConnectionRepository.class);
        SocialAuthenticationServiceLocator authServiceLocator = getDependency(applicationContext, SocialAuthenticationServiceLocator.class);
        SocialUserDetailsService socialUsersDetailsService = getDependency(applicationContext, SocialUserDetailsService.class);

        SocialAuthenticationFilter filter = new SocialAuthenticationFilter(
                http.getSharedObject(AuthenticationManager.class),
                userIdSource != null ? userIdSource : new AuthenticationNameUserIdSource(),
                usersConnectionRepository,
                authServiceLocator);

        RememberMeServices rememberMe = http.getSharedObject(RememberMeServices.class);
        if (rememberMe != null) {
            filter.setRememberMeServices(rememberMe);
        }

        if (postLoginUrl != null) {
            filter.setPostLoginUrl(postLoginUrl);
            filter.setAlwaysUsePostLoginUrl(alwaysUsePostLoginUrl);
        }

        if (postFailureUrl != null) {
//            filter.setPostFailureUrl(postFailureUrl);
            filter.setDefaultFailureUrl(postFailureUrl);
        }

        if (signupUrl != null) {
            filter.setSignupUrl(signupUrl);
        }

        if (connectionAddedRedirectUrl != null) {
            filter.setConnectionAddedRedirectUrl(connectionAddedRedirectUrl);
        }

        if (defaultFailureUrl != null) {
            filter.setDefaultFailureUrl(defaultFailureUrl);
        }

        http.authenticationProvider(
                new SocialAuthenticationProvider(usersConnectionRepository, socialUsersDetailsService))
                .addFilterBefore(postProcess(filter), AbstractPreAuthenticatedProcessingFilter.class);
    }

    private <T> T getDependency(ApplicationContext applicationContext, Class<T> dependencyType) {
        try {
            T dependency = applicationContext.getBean(dependencyType);
            return dependency;
        } catch (NoSuchBeanDefinitionException e) {
            throw new IllegalStateException("SpringSocialConfigurer depends on " + dependencyType.getName() +". No single bean of that type found in application context.", e);
        }
    }

    public SpringSocialConfigurer userIdSource(UserIdSource userIdSource) {
        this.userIdSource = userIdSource;
        return this;
    }

    public SpringSocialConfigurer postLoginUrl(String postLoginUrl) {
        this.postLoginUrl = postLoginUrl;
        return this;
    }

    public SpringSocialConfigurer alwaysUsePostLoginUrl(boolean alwaysUsePostLoginUrl) {
        this.alwaysUsePostLoginUrl = alwaysUsePostLoginUrl;
        return this;
    }

    public SpringSocialConfigurer postFailureUrl(String postFailureUrl) {
        this.postFailureUrl = postFailureUrl;
        return this;
    }

    public SpringSocialConfigurer signupUrl(String signupUrl) {
        this.signupUrl = signupUrl;
        return this;
    }

    public SpringSocialConfigurer connectionAddedRedirectUrl(String connectionAddedRedirectUrl) {
        this.connectionAddedRedirectUrl = connectionAddedRedirectUrl;
        return this;
    }

    public SpringSocialConfigurer defaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
        return this;
    }

}
