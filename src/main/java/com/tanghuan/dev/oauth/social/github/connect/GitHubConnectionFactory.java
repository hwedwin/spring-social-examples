package com.tanghuan.dev.oauth.social.github.connect;

import com.tanghuan.dev.oauth.social.github.api.GitHub;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubConnectionFactory   extends OAuth2ConnectionFactory<GitHub> {

    public GitHubConnectionFactory(String clientId, String clientSecret) {
        super("github", new GitHubServiceProvider(clientId, clientSecret), new GitHubAdapter());
    }

}
