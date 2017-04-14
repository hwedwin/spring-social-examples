package com.tanghuan.dev.oauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tanghuan on 2017/4/13.
 */

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/main", "/main.html"})
    public String main() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "main";
    }

}
