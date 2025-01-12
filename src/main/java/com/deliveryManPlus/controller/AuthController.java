package com.deliveryManPlus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.deliveryManPlus.utils.Oauth2Utils.generateState;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Value("${oauth.naver.client-id}")
    private String naverClientId;

    @Value("${oauth.naver.login-uri}")
    private String naverLoginUrl;

    @Value("${oauth.naver.redirect-uri}")
    private String naverRedirectUrl;


    @GetMapping("/login/naver")
    public String loginNaver(HttpServletRequest request) {

        // 상태 토큰으로 사용할 랜덤 문자열 생성
        String naverState = generateState();


        String url = naverLoginUrl
                + "&client_id=" + naverClientId
                + "&redirect_uri=" + naverRedirectUrl
                + "&state=" + naverState;

        request.getSession().setAttribute("state", naverState);

        return "redirect:" + url;
    }

}
