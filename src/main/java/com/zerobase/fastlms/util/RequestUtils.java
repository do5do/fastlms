package com.zerobase.fastlms.util;

import com.zerobase.fastlms.member.type.Headers;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = null;

        for (Headers header : Headers.values()) {
            ip = request.getHeader(header.getValue());
        }

        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
