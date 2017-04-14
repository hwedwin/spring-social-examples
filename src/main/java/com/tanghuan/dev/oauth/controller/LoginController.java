package com.tanghuan.dev.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Arthur on 2017/4/14.
 */

@Controller
public class LoginController {

    @GetMapping(value = {"/login.html"})
    public String login() {
        return "login";
    }

}
