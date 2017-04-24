package com.tanghuan.dev.oauth.security.repo;

import com.tanghuan.dev.oauth.repository.UserConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Arthur on 2017/4/24.
 */
public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        return null;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return null;
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        return null;
    }
}
