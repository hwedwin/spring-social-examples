package com.tanghuan.dev.oauth.social.github.api.impl;

import org.springframework.social.MissingAuthorizationException;

/**
 * Created by Arthur on 2017/4/13.
 */
public class AbstractGitHubOperations {
    private final boolean isAuthorized;

    public AbstractGitHubOperations(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException("github");
        }
    }

    protected String buildUri(String path) {
        return API_URL_BASE + path;
    }

    // GitHub API v3
    private static final String API_URL_BASE = "https://api.github.com/";
}
