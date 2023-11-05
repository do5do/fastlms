package com.zerobase.fastlms.member.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Headers {
    X_FORWARDED_FOR("X-Forwarded-For"),
    HTTP_CLIENT_IP("HTTP_CLIENT_IP"),
    HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR"),
    HTTP_X_FORWARDED("HTTP_X_FORWARDED"),
    HTTP_FORWARDED_FOR("HTTP_FORWARDED_FOR"),
    HTTP_FORWARDED("HTTP_FORWARDED"),
    PROXY_CLIENT_IP("Proxy-Client-IP"),
    WL_PROXY_CLIENT_IP("WL-Proxy-Client-IP"),
    HTTP_VIA("HTTP_VIA"),
    IPV6_ADR("IPV6_ADR");

    private final String value;
}
