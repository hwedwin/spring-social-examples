package com.tanghuan.dev.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by tanghuan on 2017/4/13.
 */

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private ConnectSupport connectSupport;

    @PostMapping(value = {"/{providerId}"})
    public RedirectView connect(@PathVariable String providerId, NativeWebRequest req) {

        ConnectionFactory<?> factory = connectionFactoryLocator.getConnectionFactory(providerId);

        return new RedirectView(connectSupport.buildOAuthUrl(factory, req));
    }

    @GetMapping(value = {"/{providerId}"}, params = {"code"})
    public String oauth2Callback(@PathVariable String providerId, NativeWebRequest req, Model model) {

        OAuth2ConnectionFactory<?> factory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);

        Connection<?> conn = connectSupport.completeConnection(factory, req);

        model.addAttribute("name", conn.getDisplayName());

        return "main";
    }

}