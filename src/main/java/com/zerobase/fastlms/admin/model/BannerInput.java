package com.zerobase.fastlms.admin.model;

import lombok.Data;

@Data
public class BannerInput {
    private Long id;
    private String bannerName;
    private String link;
    private String target;
    private Integer sorting;
    private boolean isOpen;

    //삭제를 위한
    private String idList;

    //ADD
    private String filename;
    private String urlFileame;
}
