package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.LoginHistoryService;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final LoginHistoryService loginHistoryService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인에 성공하였습니다.");

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        loginHistoryService.saveLoginHistory(principal.getUsername(),
                RequestUtils.getUserAgent(request),
                RequestUtils.getClientIp(request));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
