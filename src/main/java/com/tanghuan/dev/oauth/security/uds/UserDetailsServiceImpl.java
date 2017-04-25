package com.tanghuan.dev.oauth.security.uds;

import com.tanghuan.dev.oauth.entity.domain.User;
import com.tanghuan.dev.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Arthur on 2017/4/14.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户名：%s 不存在。", username));
        }

        return user;
    }
}
