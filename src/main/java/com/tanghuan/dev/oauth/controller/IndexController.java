package com.tanghuan.dev.oauth.controller;

import com.tanghuan.dev.oauth.entity.domain.Role;
import com.tanghuan.dev.oauth.entity.domain.User;
import com.tanghuan.dev.oauth.entity.dto.UserBindDto;
import com.tanghuan.dev.oauth.repository.RoleRepository;
import com.tanghuan.dev.oauth.repository.UserRepository;
import com.tanghuan.dev.oauth.security.annotation.CurrentUser;
import com.tanghuan.dev.oauth.security.utils.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUser;
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

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String index(@CurrentUser SocialUser user, Model model) {

        List<Object> principals = sessionRegistry.getAllPrincipals();

        model.addAttribute("username", user);
        model.addAttribute("num", principals.size());

        return "index";
    }

    @GetMapping("/signin")
    public String signin(WebRequest request) {

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        return "redirect:/login.html";
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

        String userId = connection.getKey().toString();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            user = new User();
            user.setDisplayName(connection.getDisplayName());
            user.setUserId(userId);
            user.setPhone(dto.getPhone());

            Role role = roleRepository.findByRole("USER");
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }

        SignInUtils.signin(userId);
        providerSignInUtils.doPostSignUp(userId, request);

        return "redirect:/index.html";
    }

}
