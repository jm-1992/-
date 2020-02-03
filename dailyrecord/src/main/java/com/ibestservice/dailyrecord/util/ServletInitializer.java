package com.ibestservice.dailyrecord.util;

import com.ibestservice.dailyrecord.DailyRecordApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DailyRecordApplication.class);
    }

}
