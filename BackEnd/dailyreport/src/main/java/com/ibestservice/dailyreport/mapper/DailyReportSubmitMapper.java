package com.ibestservice.dailyreport.mapper;

import com.ibestservice.dailyreport.bean.ProjectJournalBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyReportSubmitMapper {

    ProjectJournalBean getEmplById(String emplId);

    ProjectJournalBean getEmplByProjectId(String projectId);

    void deleteJournalByEmplId(String emplId);

    void insertJournals(List<ProjectJournalBean> projectJournalBeans);
}
