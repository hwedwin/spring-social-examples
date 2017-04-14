package com.tanghuan.dev.oauth.security.uds;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Created by Arthur on 2017/4/14.
 */
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return null;
    }

}
