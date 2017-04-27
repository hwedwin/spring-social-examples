package com.tanghuan.dev.oauth.social.weichat.api.impl;

import org.springframework.social.MissingAuthorizationException;

/**
 * Created by Arthur on 2017/4/27.
 */
public class AbstractWeiChatOperations {
    private final boolean isAuthorized;

    public AbstractWeiChatOperations(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException("weichat");
        }
    }

    protected String buildUri(String path) {
        return API_URL_BASE + path;
    }

    // GitHub API v3
    private static final String API_URL_BASE = "https://api.weixin.qq.com/";
}
