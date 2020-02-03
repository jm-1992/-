package com.ibestservice.dailyreport.service.impl;

import com.ibestservice.dailyreport.bean.VacationBean;
import com.ibestservice.dailyreport.mapper.VacationMapper;
import com.ibestservice.dailyreport.service.VacationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class VacationServiceImpl implements VacationService {

    @Resource
    VacationMapper vacationMapper;

    public int insertVacation(List<VacationBean> list){
        return vacationMapper.insertVacation(list);
    }
}
