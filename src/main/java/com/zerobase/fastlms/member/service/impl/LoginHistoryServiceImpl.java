package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public void saveLoginHistory(String userId, String userAgent, String clientIp) {
        loginHistoryRepository.save(
                LoginHistory.of(userId, userAgent, clientIp));
    }

    @Override
    public List<LoginHistoryDto> detail(String userId) {
        Pageable limit = PageRequest.of(0, 20);
        return loginHistoryRepository.findAllByLoginIdOrderById(userId, limit)
                .stream()
                .map(LoginHistoryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
