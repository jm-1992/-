package com.ibestservice.dailyreport.service.impl;

import com.ibestservice.dailyreport.bean.ProjectJournalBean;
import com.ibestservice.dailyreport.mapper.DailyReportSubmitMapper;
import com.ibestservice.dailyreport.service.DailyReportSubmitService;
import com.ibestservice.dailyreport.util.AnalysisExcelData;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DailyReportSubmitServiceImpl implements DailyReportSubmitService {

    Logger logger = LogManager.getLogger(DailyReportSubmitServiceImpl.class);
    @Autowired
    private DailyReportSubmitMapper dailyReportSubmitMapper;


    @Override
    public String submitDailyReport(MultipartFile file) throws Exception {
        List<List> excelData = AnalysisExcelData.getExcelData(file);
        logger.info("excel解析出来内容："+excelData.toString());
        //循环解析出来的列表，其中姓名，工号等内容是固定栏位,
        JSONObject returnjson = new JSONObject();
        String emplName= (String) excelData.get(0).get(1);
        String emplId = (String) excelData.get(0).get(4);
        if(StringUtils.isEmpty(emplId) ||StringUtils.isEmpty(emplName)){
            returnjson.put("code","0");
            returnjson.put("message","员工姓名和工号不能为空");
            return returnjson.toString();
        }
        //验证该工号是否在数据库对应表中,根据工号查询该员工是否存在
        ProjectJournalBean projectJournalBean = dailyReportSubmitMapper.getEmplById(emplId);
        if(null==projectJournalBean){
            returnjson.put("code","0");
            returnjson.put("message","工号错误或者不存在");
            return returnjson.toString();
        }
        List<ProjectJournalBean> projectJournalBeans = new ArrayList<>();
        String summary="";
        for(int i=3;i<excelData.size();i++){
            //查询每行的第一字段值是否是工作总结，如果是，下一行为工作总结，如果不是，则为工作任务继续循环
            String summaryRow = (String)excelData.get(i).get(0);
            if(!StringUtils.isEmpty(summaryRow) && summaryRow.equals("工作总结")){
                if(i+1<excelData.size()){
                    summary=(String)excelData.get(i+1).get(0);
                }
                break;
            }
        }
        //循环查询工作任务
        for(int i=3;i<excelData.size();i++){
            String projectId = (String) excelData.get(i).get(2);
            if(StringUtils.isEmpty(projectId)){
                continue;
            }
            //根据projectId查询对应的项目名称信息，如果有则为正确的编号，没有，则提示异常
            ProjectJournalBean projectJournalBean0 = dailyReportSubmitMapper.getEmplByProjectId(projectId);
            if(null==projectJournalBean0){
                returnjson.put("code","0");
                returnjson.put("message","项目编号错误或者不存在");
                return returnjson.toString();
            }
            String jobContent=(String)excelData.get(i).get(3);
            String projectName=(String)excelData.get(i).get(1);
            String workingHours=(String)excelData.get(i).get(6);
            if(StringUtils.isEmpty(jobContent) || StringUtils.isEmpty(projectName) || StringUtils.isEmpty(workingHours)){
                returnjson.put("code","0");
                returnjson.put("message","工作内容，项目名称和工作时长不能为空");
                return returnjson.toString();
            }
            //存储工作任务
            ProjectJournalBean pro = new ProjectJournalBean();
            pro.setSummary(summary);
            pro.setEmplId(emplId);
            pro.setEmplName(emplName);
            pro.setProjectId(projectId);
            pro.setProjectName(projectName);
            pro.setJobContent(jobContent);
            pro.setWorkingHours(workingHours);
            projectJournalBeans.add(pro);
        }
        //先删除用户当天数据，在批量添加
        dailyReportSubmitMapper.deleteJournalByEmplId(emplId);
        //批量添加
        dailyReportSubmitMapper.insertJournals(projectJournalBeans);
        returnjson.put("code","1");
        returnjson.put("message","提交完成");
        return returnjson.toString();
    }
}
