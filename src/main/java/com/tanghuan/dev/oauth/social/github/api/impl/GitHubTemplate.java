package com.tanghuan.dev.oauth.social.github.api.impl;

import com.tanghuan.dev.oauth.social.github.api.GitHub;
import com.tanghuan.dev.oauth.social.github.api.UserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubTemplate extends AbstractOAuth2ApiBinding implements GitHub {

    private UserOperations userOperations;

    public GitHubTemplate() {
        userOperations = new UserTemplate(getRestTemplate(), isAuthorized());
    }

    public GitHubTemplate(String accessToken) {
        super(accessToken);
        userOperations = new UserTemplate(getRestTemplate(), isAuthorized());
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }
}