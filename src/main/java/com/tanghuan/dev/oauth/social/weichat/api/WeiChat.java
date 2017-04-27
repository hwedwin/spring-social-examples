package com.tanghuan.dev.oauth.social.weichat.api;

import org.springframework.social.ApiBinding;

/**
 * Created by Arthur on 2017/4/27.
 */
public interface WeiChat extends ApiBinding {

    WeiChatUserOperations userOperations();

}
