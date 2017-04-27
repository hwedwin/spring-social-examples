package com.tanghuan.dev.oauth.social.github.api;

import org.springframework.social.ApiBinding;

/**
 * Created by Arthur on 2017/4/13.
 */
public interface GitHub  extends ApiBinding {

    GitHubUserOperations userOperations();

}
