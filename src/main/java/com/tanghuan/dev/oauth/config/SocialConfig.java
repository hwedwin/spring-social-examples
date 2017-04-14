package com.tanghuan.dev.oauth.config;

import com.tanghuan.dev.oauth.social.github.connect.GitHubConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectSupport;

/**
 * Created by Arthur on 2017/4/13.
 */

@Configuration
public class SocialConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(
                new GitHubConnectionFactory(env.getProperty("oauth.github.clientId"), env.getProperty("oauth.github.clientSecret"))
        );
        return registry;
    }

    @Bean
    public ConnectSupport connectSupport() {
        return new ConnectSupport();
    }

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(){
        return usersConnectionRepository().createConnectionRepository("6666");
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator());
    }

}
