package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {
    private final BannerMapper bannerMapper;
    private final BannerRepository bannerRepository;

    @Override
    public List<BannerDto> list(BannerParam parameter) {
        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public BannerDto findById(Long id) {
        return bannerRepository.findById(id)
                .map(BannerDto::fromEntity).orElse(null);
    }

    @Override
    public boolean update(BannerInput parameter) {
        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());
        if (!optionalBanner.isPresent()) {
            return false;
        }

        Banner banner = Banner.builder()
                .bannerName(parameter.getBannerName())
                .link(parameter.getLink())
                .target(parameter.getTarget())
                .sorting(parameter.getSorting())
                .isOpen(parameter.isOpen())
                .filename(parameter.getFilename())
                .urlFileame(parameter.getUrlFileame())
                .udtDt(LocalDateTime.now())
                .build();

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public boolean add(BannerInput parameter) {
        Banner banner = Banner.builder()
                .bannerName(parameter.getBannerName())
                .link(parameter.getLink())
                .target(parameter.getTarget())
                .sorting(parameter.getSorting())
                .isOpen(parameter.isOpen())
                .filename(parameter.getFilename())
                .urlFileame(parameter.getUrlFileame())
                .regDt(LocalDateTime.now())
                .build();

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public boolean del(String idList) {
        if (idList != null && !idList.isEmpty()) {
            String[] ids = idList.split(",");

            for (String x : ids) {
                long id = 0L;

                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                    log.error("id parse error");
                }

                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public List<BannerDto> findOpenBanner() {
        return bannerRepository.findAllByIsOpenOrderBySorting(true)
                .stream().map(BannerDto::fromEntity)
                .collect(Collectors.toList());
    }
}
