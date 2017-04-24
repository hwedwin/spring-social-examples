package com.tanghuan.dev.oauth.security.repo;

import com.tanghuan.dev.oauth.entity.domain.UserConnection;
import com.tanghuan.dev.oauth.repository.UserConnectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Created by Arthur on 2017/4/24.
 */
public class ConnectionRepositoryImpl implements ConnectionRepository {

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    private final String userId;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    public ConnectionRepositoryImpl(String userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.userId = userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {

        List<UserConnection> userConnections = userConnectionRepository.findByUserIdOrderByProviderIdAscRankAsc(userId);

        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<>();

        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();

        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.emptyList());
        }

        for (UserConnection userConnection : userConnections) {
            String providerId = userConnection.getProviderId();

            if (connections.get(userConnection).size() == 0) {
                connections.put(providerId, new LinkedList<>());
            }

            connections.add(providerId, map(userConnection));
        }

        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {

        List<UserConnection> userConnections = userConnectionRepository.findByUserIdAndProviderIdOrderByRankAsc(userId, providerId);

        List<Connection<?>> connections = new ArrayList<>();

        for (UserConnection userConnection : userConnections) {
            connections.add(map(userConnection));
        }

        return connections;
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {

        List<?> connections = findConnections(getProviderId(apiType));

        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {

        if (providerUserIds == null || providerUserIds.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        // TODO

        return null;
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {

        UserConnection userConnection = userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());

        return map(userConnection);
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {

        String providerId = getProviderId(apiType);

        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {

        String providerId = getProviderId(apiType);

        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);

        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    public void addConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();

        UserConnection userConnection = userConnectionRepository.findFirstByUserIdAndProviderIdOrderByRankDesc(userId, data.getProviderId());

        if (userConnection == null) {
            userConnection = new UserConnection();
        }

        BeanUtils.copyProperties(data, userConnection);
        userConnection.setUserId(userId);
        userConnection.setRank(userConnection.getRank() == null ? new Integer(1) : userConnection.getRank().intValue() > 0 ? userConnection.getRank(): new Integer(1));
        userConnection.setAccessToken(encrypt(data.getAccessToken()));
        userConnection.setSecret(encrypt(data.getSecret()));
        userConnection.setRefreshToken(encrypt(data.getRefreshToken()));

        userConnectionRepository.save(userConnection);
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();
        UserConnection userConnection = userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, data.getProviderId(), data.getProviderUserId());

        if (userConnection != null) {
            userConnection.setDisplayName(data.getDisplayName());
            userConnection.setProfileUrl(data.getProfileUrl());
            userConnection.setImageUrl(data.getImageUrl());
            userConnection.setAccessToken(encrypt(data.getAccessToken()));
            userConnection.setSecret(encrypt(data.getSecret()));
            userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
            userConnection.setExpireTime(data.getExpireTime());

            userConnectionRepository.save(userConnection);
        }
    }

    @Override
    public void removeConnections(String providerId) {
        List<UserConnection> userConnections = userConnectionRepository.findByProviderId(providerId);
        userConnectionRepository.deleteInBatch(userConnections);
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        UserConnection userConnection = userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        userConnectionRepository.delete(userConnection);
    }
    private Connection<?> findPrimaryConnection(String providerId) {

        List<UserConnection> userConnections = userConnectionRepository.findByUserIdAndProviderIdOrderByRankAsc(userId, providerId);

        if (userConnections != null && userConnections.size() > 0) {
            return map(userConnections.get(0));
        } else {
            return null;
        }
    }


    private Connection<?> map(UserConnection userConnection) {

        if (userConnection == null) return null;

        ConnectionData connectionData = new ConnectionData(
            userConnection.getProviderId(),
            userConnection.getProviderUserId(),
            userConnection.getDisplayName(),
            userConnection.getProfileUrl(),
            userConnection.getImageUrl(),
            decrypt(userConnection.getAccessToken()),
            decrypt(userConnection.getSecret()),
            decrypt(userConnection.getRefreshToken()),
            expireTime(userConnection.getExpireTime())
        );

        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());

        return connectionFactory.createConnection(connectionData);
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }

    private String decrypt(String encryptedText) {
        return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
    }

    private Long expireTime(long expireTime) {
        return expireTime == 0 ? null : expireTime;
    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }
}
