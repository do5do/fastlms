package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryDto {
    private LocalDateTime loginDate;
    private String clientIp;
    private String userAgent;

    public static LoginHistoryDto fromEntity(LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
                .loginDate(loginHistory.getLoginDate())
                .clientIp(loginHistory.getClientIp())
                .userAgent(loginHistory.getUserAgent())
                .build();
    }

    public String getLoginDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginDate != null ? loginDate.format(formatter) : "";
    }
}
