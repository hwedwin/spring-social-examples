package com.tanghuan.dev.oauth.social.weichat.connect;

import com.tanghuan.dev.oauth.social.weichat.api.WeiChat;
import com.tanghuan.dev.oauth.social.weichat.api.impl.WeiChatTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by Arthur on 2017/4/27.
 */
public class WeiChatServiceProvider extends AbstractOAuth2ServiceProvider<WeiChat> {

    public WeiChatServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(
                clientId,
                clientSecret,
                "https://open.weixin.qq.com/connect/qrconnect",
                "/sns/oauth2/access_token"
        ));
    }

    @Override
    public WeiChat getApi(String accessToken) {
        return new WeiChatTemplate(accessToken);
    }
}
