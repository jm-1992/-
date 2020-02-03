package com.ibestservice.dailyreport.service.impl;

import com.ibestservice.dailyreport.mapper.RecordMapper;
import com.ibestservice.dailyreport.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService {

    @Resource
    RecordMapper recordMapper;

    public void insertRecordService(Map<String,String> map) {
        recordMapper.insertRecord(map);
    }
}
