package com.tanghuan.dev.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tanghuan on 2017/4/13.
 */

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String index() {
        System.out.println("12344");
        return "index";
    }

}
