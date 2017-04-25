package com.tanghuan.dev.oauth;

import com.tanghuan.dev.oauth.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Arthur on 2017/4/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseTest {
    @Test
    public void test() throws Exception {

        System.out.println("Hello Junit!!!");

    }
}
