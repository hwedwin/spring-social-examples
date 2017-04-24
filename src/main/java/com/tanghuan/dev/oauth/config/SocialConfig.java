package com.tanghuan.dev.oauth.config;

import com.tanghuan.dev.oauth.security.repo.UsersConnectionRepositoryImpl;
import com.tanghuan.dev.oauth.security.uds.SocialUserDetailsServiceImpl;
import com.tanghuan.dev.oauth.social.github.api.GitHub;
import com.tanghuan.dev.oauth.social.github.connect.GitHubConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;

import javax.sql.DataSource;

/**
 * Created by Arthur on 2017/4/13.
 */

@Configuration
@EnableSocial
@Import(value = {JpaConfig.class, WebSecurityConfig.class})
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment env) {
        connectionFactoryConfigurer.addConnectionFactory(
                new GitHubConnectionFactory(env.getProperty("oauth.github.clientId"), env.getProperty("oauth.github.clientSecret"))
        );
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new UsersConnectionRepositoryImpl(connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SocialUserDetailsServiceImpl();
    }

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    public GitHub gitHub(ConnectionRepository repository) {
        Connection<GitHub> connection = repository.findPrimaryConnection(GitHub.class);
        return connection != null ? connection.getApi() : null;
    }
}
