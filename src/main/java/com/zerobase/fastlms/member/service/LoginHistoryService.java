package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;

import java.util.List;

public interface LoginHistoryService {
    void saveLoginHistory(String userId, String userAgent, String clientIp);

    List<LoginHistoryDto> detail(String userId);
}
