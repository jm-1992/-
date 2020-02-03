package com.ibestservice.dailyreport.service;

import com.ibestservice.dailyreport.bean.ProjectJournalBean;
import org.springframework.web.multipart.MultipartFile;

public interface DailyReportSubmitService {

    /**
     * 解析上传excel文件内容入库
     * @param file
     * @return
     */
    String submitDailyReport(MultipartFile file) throws Exception;
}
