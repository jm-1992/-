package com.ibestservice.dailyreport.controller;


import com.ibestservice.dailyreport.service.DailyReportSubmitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/submitReport")
public class DailyReportSubmitController {

    Logger logger = LogManager.getLogger(DailyReportSubmitController.class);
    @Autowired
    private DailyReportSubmitService dailyReportSubmitService;

    @ResponseBody
    @RequestMapping(value = "/submitDailyReport")
    public String submitDailyReport(HttpServletRequest request, @RequestParam(value="file") CommonsMultipartFile file) throws Exception {
        logger.info("进入后台提交，文件名："+file.getOriginalFilename());
        return dailyReportSubmitService.submitDailyReport(file);
    }
}
