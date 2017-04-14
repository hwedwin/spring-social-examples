package com.tanghuan.dev.oauth.controller;

import com.tanghuan.dev.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Arthur on 2017/4/14.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/users.html"})
    public String users() {
        return "redirect:/index.html";
    }

}
