package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {
    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter) {
        parameter.init();
        List<BannerDto> banners = bannerService.list(parameter);

        long totalCount = 0;
        if (banners != null && !banners.isEmpty()) {
            totalCount = banners.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", banners);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request, BannerInput parameter) {
        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        if (editMode) {
            BannerDto existsBanner = bannerService.findById(parameter.getId());
            if (existsBanner == null) {
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existsBanner;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/banner/add";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request, MultipartFile file,
                            BannerInput parameter) {
        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            String baseLocalPath = "/Users/doheekim/Downloads/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFileame(urlFilename);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {
            long id = parameter.getId();
            BannerDto existBanner = bannerService.findById(id);
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "존재하지 않는 배너입니다.");
                return "common/error";
            }
            bannerService.update(parameter);
        } else {
            bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(BannerInput parameter) {
        bannerService.del(parameter.getIdList());
        return "redirect:/admin/banner/list.do";
    }
}
