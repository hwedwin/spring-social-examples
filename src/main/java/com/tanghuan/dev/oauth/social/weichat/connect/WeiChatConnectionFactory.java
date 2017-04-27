package com.tanghuan.dev.oauth.social.weichat.connect;

import com.tanghuan.dev.oauth.social.weichat.api.WeiChat;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * Created by Arthur on 2017/4/27.
 */
public class WeiChatConnectionFactory extends OAuth2ConnectionFactory<WeiChat> {

    public WeiChatConnectionFactory(String clientId, String clientSecret) {
        super("weichat", new WeiChatServiceProvider(clientId, clientSecret), new WeiChatAdapter());
    }
}
