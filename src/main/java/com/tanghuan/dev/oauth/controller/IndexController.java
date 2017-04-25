package com.tanghuan.dev.oauth.controller;

import com.tanghuan.dev.oauth.entity.domain.Role;
import com.tanghuan.dev.oauth.entity.domain.User;
import com.tanghuan.dev.oauth.entity.dto.UserBindDto;
import com.tanghuan.dev.oauth.entity.dto.UserDto;
import com.tanghuan.dev.oauth.repository.RoleRepository;
import com.tanghuan.dev.oauth.repository.UserRepository;
import com.tanghuan.dev.oauth.security.utils.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanghuan on 2017/4/13.
 */

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

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
    public String signup(WebRequest request, Model model) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection == null) {
            return "redirect:/login.html";
        }
        model.addAttribute("displayName", connection.getDisplayName());
        model.addAttribute("imageUrl", connection.getImageUrl());
        return "signup";
    }

    @PostMapping("/signup")
    public String bindPhone(WebRequest request, @Valid UserBindDto dto) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        if (connection == null) {
            return "redirect:/login.html";
        }

        // TODO 验证手机验证码的正确性

        // TODO 保存用户和第三方应用账号的关系
        User user = new User();
        user.setDisplayName(connection.getDisplayName());
        String userId = connection.getKey().toString();
        user.setUserId(userId);
        user.setPhone(dto.getPhone());

        Role role = roleRepository.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        SignInUtils.signin(userId);
        providerSignInUtils.doPostSignUp(userId, request);

        return "redirect:/index.html";
    }

}
