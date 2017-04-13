package com.tanghuan.dev.oauth.social.github.connect;

import com.tanghuan.dev.oauth.social.github.api.GitHub;
import com.tanghuan.dev.oauth.social.github.api.impl.GitHubTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubServiceProvider extends AbstractOAuth2ServiceProvider<GitHub> {


    public GitHubServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(
                clientId,
                clientSecret,
                "https://github.com/login/oauth/authorize",
                "https://github.com/login/oauth/access_token"
        ));
    }

    @Override
    public GitHub getApi(String accessToken) {
        return new GitHubTemplate(accessToken);
    }

}