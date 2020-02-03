package com.ibestservice.dailyrecord;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.ibestservice.dailyrecord.mapper")
@EnableTransactionManagement
public class DailyRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyRecordApplication.class, args);
    }

}
