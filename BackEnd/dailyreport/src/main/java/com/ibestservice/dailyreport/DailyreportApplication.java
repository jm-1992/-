package com.ibestservice.dailyreport;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.ibestservice.dailyreport.mapper")
@EnableTransactionManagement
public class DailyreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyreportApplication.class, args);
    }

}
