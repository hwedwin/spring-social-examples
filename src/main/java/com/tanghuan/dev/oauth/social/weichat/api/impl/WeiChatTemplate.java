package com.tanghuan.dev.oauth.social.weichat.api.impl;

import com.tanghuan.dev.oauth.social.weichat.api.WeiChat;
import com.tanghuan.dev.oauth.social.weichat.api.WeiChatUserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * Created by Arthur on 2017/4/27.
 */
public class WeiChatTemplate extends AbstractOAuth2ApiBinding implements WeiChat {

    private WeiChatUserTemplate userOperations;

    public WeiChatTemplate() {
        userOperations = new WeiChatUserTemplate(getRestTemplate(), isAuthorized());
    }

    public WeiChatTemplate(String accessToken) {
        super(accessToken);
        userOperations = new WeiChatUserTemplate(getRestTemplate(), isAuthorized());
    }

    @Override
    public WeiChatUserOperations userOperations() {
        return userOperations;
    }

}
