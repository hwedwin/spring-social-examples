package com.tanghuan.dev.oauth.social.github.api.impl;

import com.tanghuan.dev.oauth.social.github.api.GitHub;
import com.tanghuan.dev.oauth.social.github.api.GitHubUserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubTemplate extends AbstractOAuth2ApiBinding implements GitHub {

    private GitHubUserOperations userOperations;

    public GitHubTemplate() {
        userOperations = new GitHubUserTemplate(getRestTemplate(), isAuthorized());
    }

    public GitHubTemplate(String accessToken) {
        super(accessToken);
        userOperations = new GitHubUserTemplate(getRestTemplate(), isAuthorized());
    }

    @Override
    public GitHubUserOperations userOperations() {
        return userOperations;
    }
}