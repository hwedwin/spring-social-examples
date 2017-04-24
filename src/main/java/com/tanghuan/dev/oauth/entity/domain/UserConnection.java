package com.tanghuan.dev.oauth.entity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Arthur on 2017/4/24.
 */

@Entity
@Table(name = "t_user_connection")
public class UserConnection extends Super {
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "provider_user_id")
    private String providerUserId;

    @Column(nullable = false)
    private Integer rank;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "profile_url", length = 512)
    private String profileUrl;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "access_token", length = 512, nullable = false)
    private String accessToken;

    @Column(length = 512)
    private String secret;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @Column(name = "expire_time")
    private Date expireTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
