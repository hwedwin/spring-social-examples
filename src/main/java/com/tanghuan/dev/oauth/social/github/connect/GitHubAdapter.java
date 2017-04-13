package com.tanghuan.dev.oauth.social.github.connect;

import com.tanghuan.dev.oauth.social.github.api.GitHub;
import com.tanghuan.dev.oauth.social.github.api.GitHubUserProfile;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubAdapter implements ApiAdapter<GitHub> {

    @Override
    public boolean test(GitHub gitHub) {

        try {
            gitHub.userOperations().getUserProfile();

            return true;

        } catch (HttpClientErrorException e) {
            return false;

        }

    }

    @Override
    public void setConnectionValues(GitHub gitHub, ConnectionValues values) {
        GitHubUserProfile profile = gitHub.userOperations().getUserProfile();

        values.setProviderUserId(Long.toString(profile.getId()));
        values.setDisplayName(profile.getLogin());
        values.setProfileUrl(String.format("https://github.com/%s", profile.getLogin()));
        values.setImageUrl(profile.getAvatarUrl());
    }

    @Override
    public UserProfile fetchUserProfile(GitHub gitHub) {
        GitHubUserProfile profile = gitHub.userOperations().getUserProfile();

        return new UserProfileBuilder()
                .setName(profile.getName())
                .setEmail(profile.getEmail())
                .setUsername(profile.getLogin())
                .build();
    }

    @Override
    public void updateStatus(GitHub api, String message) {

    }

}