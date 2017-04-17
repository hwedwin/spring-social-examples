package com.tanghuan.dev.oauth.repository;

import com.tanghuan.dev.oauth.entity.domain.User;

/**
 * Created by Arthur on 2017/4/14.
 */
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);

}
