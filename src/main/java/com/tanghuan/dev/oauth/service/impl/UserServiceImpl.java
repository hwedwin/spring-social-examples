package com.tanghuan.dev.oauth.service.impl;

import com.tanghuan.dev.oauth.repository.UserRepository;
import com.tanghuan.dev.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Arthur on 2017/4/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


}
