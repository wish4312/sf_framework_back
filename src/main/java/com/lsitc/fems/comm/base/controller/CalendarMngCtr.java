/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 LS ITC에 있으며,
* LS ITC가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* LS ITC의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2021 LS ITC. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* LS ITC Business unit. LS ITC Business unit., owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2021 LS ITC Business unit. All Rights Reserved| Confidential)
* Author    : LS ITC
* Created   : 2021-04-23 17:58:11
*/
 
package com.lsitc.fems.comm.base.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.fems.comm.base.service.CalendarMngSvc;
import com.lsitc.fems.comm.base.vo.WorkCaldVo;

@RequestMapping("/comm/base/CalendarMngCtr")
@Controller
public class CalendarMngCtr{

    @Autowired
    private CalendarMngSvc calendarMngSvc;

    /**
     * @methodName  : selectWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWorkCald", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWorkcald(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("workcaldData", calendarMngSvc.selectWorkCald(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveWorkCald", method=RequestMethod.POST)
    @ResponseBody
    public Object saveWorkcald(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        calendarMngSvc.saveWorkCald(paramMap.getDs("dsWorkcald", WorkCaldVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectMonthWeekendList
     * @date        : 2021.06.07
     * @desc        : 특정 기간 및 월에 대한 휴일 목록 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMonthWeekendList", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMonthWeekendList(@RequestBody(required = false) HashMap<String, Object> paramMap){
        BaseResponse result = new BaseResponse();
        HashMap<String, Object> param = new HashMap<String, Object>();
        String dt = (String) paramMap.get("sh_date");
        String startDt = (String) paramMap.get("sh_startDt");
        String endDt = (String) paramMap.get("sh_endDt");
        String sh_date = "";
        String sh_startDt = "";
        String sh_endDt = "";
        List<String> hldyNmNotInList = new ArrayList<String>();
        hldyNmNotInList.add("토요일");
        hldyNmNotInList.add("일요일");
        
        if(dt != null) {
            sh_date = dt.replaceAll("/", "-");
            dt = dt.replaceAll("-", "");
            dt = dt.replaceAll("/", "");
        }
        if(startDt != null) {
            sh_startDt = startDt.replaceAll("/", "-");
            startDt = startDt.replaceAll("-", "");
            startDt = startDt.replaceAll("/", "");
        }
        if(endDt != null) {
            sh_endDt = endDt.replaceAll("/", "-");
            endDt = endDt.replaceAll("-", "");
            endDt = endDt.replaceAll("/", "");
        }

        param.put("dtLike", sh_date);
        param.put("startDt", sh_startDt);
        param.put("endDt", sh_endDt);
        param.put("comId", paramMap.get("sh_comId"));
        param.put("blocId", paramMap.get("sh_blocId"));
        param.put("hldyFg", "1");
        param.put("hldyNmNotInList", hldyNmNotInList);
        
        result.add("workcaldData", calendarMngSvc.selectWorkCaldAll(param));
        
        if(dt != null && !"".equals(dt) && dt.length() >= 6) {
            String strYear = dt.substring(0, 4);
            String strMonth = dt.substring(4, 6);
            
            HashMap<String, Object> weekList = getMonthStdSunDayList(Integer.parseInt(strYear), Integer.parseInt(strMonth));
            
            result.add("dayOfMonth", weekList.get("dayOfMonth"));
            result.add("staList", weekList.get("staList"));
            result.add("sunList", weekList.get("sunList"));
        }
        
        if((dt == null || "".equals(dt)) && startDt != null && !"".equals(startDt) && startDt.length() >= 8 && 
                endDt != null && !"".equals(endDt) && endDt.length() >= 8) {
            String strStartYear = startDt.substring(0, 4);
            String strStartMonth = startDt.substring(4, 6);
            String strStartDay = startDt.substring(6, 8);
            String strEndYear = endDt.substring(0, 4);
            String strEndMonth = endDt.substring(4, 6);
            String strEndDay = endDt.substring(6, 8);
            
            HashMap<String, Object> weekList = getMonthStdSunDayList(Integer.parseInt(strStartYear), Integer.parseInt(strStartMonth), Integer.parseInt(strStartDay)
                                                                    ,Integer.parseInt(strEndYear), Integer.parseInt(strEndMonth), Integer.parseInt(strEndDay));
            
            result.add("dayOfMonth", weekList.get("dayOfMonth"));
            result.add("staList", weekList.get("staList"));
            result.add("sunList", weekList.get("sunList"));
            
        }
        
        result.add("sh_date", paramMap.get("sh_date"));
        result.add("sh_searchType", paramMap.get("sh_searchType"));
        
        return result;
    }
    

    private HashMap<String, Object> getMonthStdSunDayList(int year, int month){
        HashMap<String, Object> result = new HashMap<String, Object>();
        int dayOfMonth = 0;
        List<String> staList = new ArrayList<String>();
        List<String> sunList = new ArrayList<String>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = new GregorianCalendar();

        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DATE, 1);

        dayOfMonth = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        for(int i = 1; i <= dayOfMonth; i++) {
            if( ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
                sunList.add(sdf.format(ca.getTime()));
            }else if(ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                staList.add(sdf.format(ca.getTime()));
            }
            ca.add(Calendar.DATE, 1);
        }
        result.put("dayOfMonth", dayOfMonth);
        result.put("staList", staList);
        result.put("sunList", sunList);
        
        return result;
    }
    
    /**
     * @methodName  : getMonthStdSunDayList
     * @date        : 2021.06.07
     * @desc        : 토,일의 목록을 구한다.
     * @param startYear
     * @param startMonth
     * @param startDay
     * @param endYear
     * @param endMonth
     * @param endDay
     * @return
     */
    private HashMap<String, Object> getMonthStdSunDayList(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        HashMap<String, Object> result = new HashMap<String, Object>();
        int dayOfMonth = 0;
        List<String> staList = new ArrayList<String>();
        List<String> sunList = new ArrayList<String>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = new GregorianCalendar();

        ca.set(Calendar.YEAR, startYear);
        ca.set(Calendar.MONTH, startMonth - 1);
        ca.set(Calendar.DATE, startDay);
        
        Calendar endCa = new GregorianCalendar();
        endCa.set(Calendar.YEAR, endYear);
        endCa.set(Calendar.MONTH, endMonth - 1);
        endCa.set(Calendar.DATE, endDay);


        Long diffSec = (endCa.getTimeInMillis() - ca.getTimeInMillis()) / 1000;
        Long diffDays = diffSec / (24*60*60); //일자수 차이
        
        dayOfMonth = diffDays.intValue();
        
        for(int i = 1; i <= dayOfMonth; i++) {
            if( ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
                sunList.add(sdf.format(ca.getTime()));
            }else if(ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                staList.add(sdf.format(ca.getTime()));
            }
            ca.add(Calendar.DATE, 1);
        }
        result.put("dayOfMonth", dayOfMonth);
        result.put("staList", staList);
        result.put("sunList", sunList);
        
        return result;
    }
    
    /**
     * @methodName  : selectWorkCaldDetl
     * @date        : 2022.05.11
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWorkCaldDetl", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWorkCaldDetl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("workcaldDetlData", calendarMngSvc.selectWorkCaldDetl(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : selectWorkCaldDetlOne
     * @date        : 2022.05.11
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWorkCaldDetlOne", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWorkCaldDetlOne(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("workcaldDetlOneData", calendarMngSvc.selectWorkCaldDetlOne(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : savePlanDetl
     * @date        : 2021.06.07
     * @desc        : 근무달력 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/savePlanDetl", method=RequestMethod.POST)
    @ResponseBody
    public Object savePlanDetl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        calendarMngSvc.savePlanDetl(paramMap.getDs("savePlanDetl", WorkCaldVo.class));
        return result;
    }
    
    /**
     * @methodName  : updatePlanDetl
     * @date        : 2021.06.07
     * @desc        : 근무달력 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/updatePlanDetl", method=RequestMethod.POST)
    @ResponseBody
    public Object updatePlanDetl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        calendarMngSvc.updatePlanDetl(paramMap.getDs("updatePlanDetl", WorkCaldVo.class));
        return result;
    }
    
    /**
     * @methodName  : deletePlanDetl
     * @date        : 2021.06.07
     * @desc        : 근무달력 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/deletePlanDetl", method=RequestMethod.POST)
    @ResponseBody
    public Object deletePlanDetl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        calendarMngSvc.deletePlanDetl(paramMap.getDs("deletePlanDetl", WorkCaldVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWeekWorkCald", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWeekWorkCald(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("workcaldData", calendarMngSvc.selectWeekWorkCald(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : selectWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWeekWorkCaldDetl", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWeekWorkCaldDetl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("workcaldData", calendarMngSvc.selectWeekWorkCaldDetl(paramMap.getParams()));
        return result;
    }
    
}