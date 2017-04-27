package com.tanghuan.dev.oauth.social.weichat.api.impl;

import com.tanghuan.dev.oauth.social.weichat.api.WeiChatUserOperations;
import com.tanghuan.dev.oauth.social.weichat.api.WeiChatUserProfile;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Arthur on 2017/4/27.
 */
public class WeiChatUserTemplate extends AbstractWeiChatOperations implements WeiChatUserOperations {

    private RestTemplate restTemplate;

    public WeiChatUserTemplate(boolean isAuthorized) {
        super(isAuthorized);
    }

    public WeiChatUserTemplate(RestTemplate restTemplate, boolean authorized) {
        super(authorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public WeiChatUserProfile getUserProfile() {
        return null;
    }
}
