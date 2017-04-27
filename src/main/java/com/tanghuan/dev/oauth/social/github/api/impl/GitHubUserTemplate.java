package com.tanghuan.dev.oauth.social.github.api.impl;

import com.tanghuan.dev.oauth.social.github.api.GitHubUser;
import com.tanghuan.dev.oauth.social.github.api.GitHubUserProfile;
import com.tanghuan.dev.oauth.social.github.api.GitHubUserOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Arthur on 2017/4/13.
 */
public class GitHubUserTemplate extends AbstractGitHubOperations implements GitHubUserOperations {

    private RestTemplate restTemplate;

    public GitHubUserTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(isAuthorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public String getProfileId() {
        return getUserProfile().getLogin();
    }

    @Override
    public GitHubUserProfile getUserProfile() {
        return restTemplate.getForObject(buildUri("user"), GitHubUserProfile.class);
    }

    @Override
    public String getProfileUrl() {
        return String.format("https://github.com/%s", getUserProfile().getLogin());
    }

    @Override
    public List<GitHubUser> getFollowers(String user) {
        return Arrays.asList(restTemplate.getForObject(buildUserUri("/followers"), GitHubUser[].class, user));
    }

    @Override
    public List<GitHubUser> getFollowing(String user) {
        return Arrays.asList(restTemplate.getForObject(buildUserUri("/following"), GitHubUser[].class, user));
    }

    private String buildUserUri(String path) {
        return buildUri("users/{user}" + path);
    }

}