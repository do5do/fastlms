package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private LocalDateTime loginDate;
    private String clientIp;
    private String userAgent;

    public static LoginHistory of(String userId, String userAgent, String clientIp) {
        return LoginHistory.builder()
                .loginId(userId)
                .userAgent(userAgent)
                .clientIp(clientIp)
                .loginDate(LocalDateTime.now())
                .build();
    }
}
