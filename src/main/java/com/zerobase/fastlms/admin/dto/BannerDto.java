package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BannerDto {
    private Long id;
    private String bannerName;
    private String link;
    private String target;
    private Integer sorting;
    private boolean isOpen;

    private String filename;
    private String urlFilename;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    private long totalCount;
    private long seq;

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public static BannerDto fromEntity(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .filename(banner.getFilename())
                .link(banner.getLink())
                .target(banner.getTarget())
                .sorting(banner.getSorting())
                .isOpen(banner.isOpen())
                .filename(banner.getFilename())
                .urlFilename(banner.getUrlFileame())
                .regDt(banner.getRegDt())
                .udtDt(banner.getUdtDt())
                .build();
    }
}
