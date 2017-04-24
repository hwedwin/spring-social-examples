package com.tanghuan.dev.oauth.controller;

import com.tanghuan.dev.oauth.entity.dto.UserDto;
import com.tanghuan.dev.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Created by tanghuan on 2017/4/13.
 */

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/main", "/main.html"})
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("username", authentication.getPrincipal());

        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String bindPhone(@Valid UserDto dto) {

        System.out.println(dto);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(dto.getUsername(), null, null));

        return "redirect:/main.html";
    }

}
