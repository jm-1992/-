package com.ibestservice.dailyreport.controller;

import com.ibestservice.dailyreport.bean.VacationBean;
import com.ibestservice.dailyreport.service.RecordService;
import com.ibestservice.dailyreport.service.VacationService;
import com.ibestservice.dailyreport.util.AnalysisExcelData;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ExcelDealController {

    Logger logger = LogManager.getLogger(ExcelDealController.class);

    @Resource
    private VacationService vacationService;

    @Resource
    private RecordService recordService;

    @RequestMapping(value = "/excelDeal")
    public String excelDeal(HttpServletRequest request, @RequestParam(value="file") CommonsMultipartFile file,
                            @RequestParam String startStr, @RequestParam String endStr)
            throws Exception {
        JSONObject returnJson = new JSONObject();
        //获得Workbook工作薄对象
        Workbook workbook = AnalysisExcelData.getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        if (workbook != null) {
            //获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(1);
            if (sheet == null) {
                throw new Exception("上传内容sheet不可为空");
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            List<VacationBean> vacationBeans=new ArrayList<>();
            //循环除了所有行,如果要循环除第一行以外的就firstRowNum+1
            for (int rowNum = firstRowNum + 4; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                //上班打卡结果
                String onWorkResult=AnalysisExcelData.getCellValue(row.getCell(9));
                //下班打卡结果
                String offWorkResult=AnalysisExcelData.getCellValue(row.getCell(11));
                VacationBean vacationBean=new VacationBean();
                vacationBean.setEmplId(AnalysisExcelData.getCellValue(row.getCell(3)));
                vacationBean.setEmplName(AnalysisExcelData.getCellValue(row.getCell(0)));
                //上下班打卡结果都不为请假不统计
                if("请假".equals(onWorkResult) || "请假".equals(offWorkResult)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                    String leaveTime=AnalysisExcelData.getCellValue(row.getCell(6)).substring(0,8);
                    Date date=sdf.parse(leaveTime);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    String leaveStr=sdf1.format(date);
                    vacationBean.setLeaveDate(leaveStr);
                    String time=AnalysisExcelData.getCellValue(row.getCell(20));
                    Pattern pattern = Pattern.compile("\\d\\d\\-\\d\\d[ ]\\d\\d\\:\\d\\d");
                    Matcher matcher=pattern.matcher(time);
                    String startTime=null,endTime=null;
                    int i=0;
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    while (matcher.find()){
                        String group=matcher.group();
                        if(i==0){
                            startTime=group;
                        }else if(i==1){
                            endTime=group;
                        }
                        i++;
                    }
                    if(null!=startTime && null!=endTime){
                        String startMonth=startTime.substring(0,2);
                        String endMonth=endTime.substring(0,2);
                        Date endDate=null;
                        String year=leaveStr.substring(0,4);
                        String yearStartTime=year+"-"+startTime;
                        vacationBean.setStartTime(yearStartTime);
                        String yearEndTime=year+"-"+endTime;
                        vacationBean.setEndTime(yearEndTime);
                        if(endMonth.compareTo(startMonth)<0){
                            yearEndTime=(Integer.valueOf(year)+1)+"-"+endTime;
                        }
                        endDate=sdf2.parse(yearEndTime);
                        Date startDate=sdf2.parse(yearStartTime);
                        Date leaveDate =sdf1.parse(leaveStr);
                        if(sameDate(startDate,leaveDate)){
                            long temp=dealTime(sdf2,yearStartTime, yearEndTime);
                            if(!sameDate(startDate,endDate)){
                                temp=dealTime(sdf2,yearStartTime, yearStartTime.substring(0,11)+"17:30");
                            }
                            vacationBean.setLeaveTime(String.valueOf(temp));
                        }else if(sameDate(endDate,leaveDate)){
                            long temp=dealTime(sdf2,yearEndTime.substring(0,11)+"8:30", yearEndTime);
                            vacationBean.setLeaveTime(String.valueOf(temp));
                        }else{
                            vacationBean.setLeaveTime(String.valueOf(480));
                        }
                    }else{
                        logger.info("开始时间或结束时间获取有问题");
                        returnJson.put("code","0");
                        returnJson.put("message","开始时间或结束时间获取有问题");
                    }
                    vacationBeans.add(vacationBean);
                }
            }
            if(vacationService.insertVacation(vacationBeans)>0){
                Map<String,String> map=new HashMap<>();
                map.put("startTime",startStr);
                map.put("endTime",endStr);
                recordService.insertRecordService(map);
            }
            returnJson.put("code","1");
            returnJson.put("message","提交完成");
        }
        return returnJson.toString();
    }

    @RequestMapping(value = "/insertRecord")
    public void insertRecord(@RequestParam String startTime, @RequestParam String endTime){

    }

    public static boolean sameDate(Date d1, Date d2) {
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public static long dealTime(SimpleDateFormat sdf,String startTime,String endTime) throws ParseException {
        long time=0;
        Date startDate=sdf.parse(startTime);
        Date endDate=sdf.parse(endTime);
        //午休开始时间
        Date lunchBreakStartDate=sdf.parse(startTime.substring(0,11)+"12:00");
        //午休结束时间
        Date lunchBreakEndDate=sdf.parse(endTime.substring(0,11)+"13:00");
        long startDateTime=startDate.getTime();
        long lunchBreakStartTime=lunchBreakStartDate.getTime();
        long lunchBreakEndTime=lunchBreakEndDate.getTime();
        long endDateTime=endDate.getTime();
        if(startDateTime<=lunchBreakStartTime && lunchBreakEndTime<=endDateTime){
             time=(endDateTime-startDateTime)/(1000*60)-60;
        }else if(startDateTime>=lunchBreakStartTime && endDateTime<=lunchBreakEndTime){
             time=0;
        }else if(startDateTime<=lunchBreakEndTime && startDateTime >=lunchBreakStartTime
                && endDateTime>=lunchBreakEndTime){
            time=(endDateTime-lunchBreakEndTime)/(1000*60);
        }else if(startDateTime>=lunchBreakEndTime || endDateTime<=lunchBreakStartTime){
            time=(endDateTime-startDateTime)/(1000*60);
        }else if(startDateTime<=lunchBreakStartTime && endDateTime<=lunchBreakEndTime
                && endDateTime>=lunchBreakStartTime)  {
            time=(lunchBreakStartTime-startDateTime)/(1000*60);
        }
        return time;
    }

}
