package com.tanghuan.dev.oauth.repository;

import com.tanghuan.dev.oauth.entity.domain.UserConnection;

import java.util.List;
import java.util.Set;

/**
 * Created by Arthur on 2017/4/24.
 */
public interface UserConnectionRepository extends BaseRepository<UserConnection> {

    List<UserConnection> findByProviderId(String providerId);

    List<UserConnection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<UserConnection> findByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

    List<UserConnection> findByUserIdOrderByProviderIdAscRankAsc(String userId);

    List<UserConnection> findByUserIdAndProviderIdOrderByRankAsc(String userId, String providerId);

    UserConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    UserConnection findFirstByUserIdAndProviderIdOrderByRankDesc(String userId, String providerId);
}
