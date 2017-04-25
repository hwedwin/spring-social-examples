package com.tanghuan.dev.oauth.repository;

import com.tanghuan.dev.oauth.BaseTest;
import com.tanghuan.dev.oauth.entity.domain.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 2017/4/25.
 */
public class RoleRepositoryTest extends BaseTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void insertRolesTest() throws Exception {
        List<Role> roles = new ArrayList<>();

        Role admin = new Role();
        admin.setName("管理员");
        admin.setRole("ADMIN");
        roles.add(admin);

        Role user = new Role();
        user.setName("普通用户");
        user.setRole("USER");
        roles.add(user);

        roleRepository.save(roles);
    }
}
