package com.tanghuan.dev.oauth.social.weichat.connect;

import com.tanghuan.dev.oauth.social.weichat.api.WeiChat;
import com.tanghuan.dev.oauth.social.weichat.api.WeiChatUserProfile;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by Arthur on 2017/4/27.
 */
public class WeiChatAdapter implements ApiAdapter<WeiChat> {
    @Override
    public boolean test(WeiChat weiChat) {
        try {
            weiChat.userOperations().getUserProfile();

            return true;

        } catch (HttpClientErrorException e) {
            return false;

        }
    }

    @Override
    public void setConnectionValues(WeiChat weiChat, ConnectionValues values) {
        WeiChatUserProfile profile = weiChat.userOperations().getUserProfile();

        values.setProviderUserId(profile.getUnionid());
        values.setDisplayName(profile.getNickName());
        values.setProfileUrl(null);
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeiChat weiChat) {

        WeiChatUserProfile profile = weiChat.userOperations().getUserProfile();

        return null;
    }

    @Override
    public void updateStatus(WeiChat weiChat, String message) {

    }
}
