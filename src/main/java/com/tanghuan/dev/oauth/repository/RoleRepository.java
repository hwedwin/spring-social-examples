package com.tanghuan.dev.oauth.repository;

import com.tanghuan.dev.oauth.entity.domain.Role;

/**
 * Created by arthur on 2017/4/17.
 */
public interface RoleRepository extends BaseRepository<Role> {

    Role findByRole(String role);

}
