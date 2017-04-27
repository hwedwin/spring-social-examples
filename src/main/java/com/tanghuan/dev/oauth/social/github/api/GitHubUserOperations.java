package com.tanghuan.dev.oauth.social.github.api;

import java.util.List;

/**
 * Created by Arthur on 2017/4/13.
 */
public interface GitHubUserOperations {

    String getProfileId();

    GitHubUserProfile getUserProfile();

    String getProfileUrl();

    List<GitHubUser> getFollowers(String user);

    List<GitHubUser> getFollowing(String user);

}
