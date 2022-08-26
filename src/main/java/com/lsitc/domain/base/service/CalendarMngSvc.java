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
package com.lsitc.domain.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.domain.auth.vo.CommMenuVo;
import com.lsitc.domain.base.vo.WorkCaldVo;

@Service
public class CalendarMngSvc extends BaseSvc {

    /**
     * @methodName  : selectWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 조회
     * @param paramMap
     * @return
     */
    public List<CommMenuVo> selectWorkCald(HashMap<String, Object> params) {
        return dao.selectList("comm.base.calendarMng.selectWorkCald", params);
    }
    
    /**
     * @methodName  : saveWorkcald
     * @date        : 2021.06.07
     * @desc        : 근무달력 저장
     * @param paramMap
     * @return
     */
    @Transactional
    public void saveWorkCald(List<WorkCaldVo> workCaldList) {
        for(WorkCaldVo eachRow : workCaldList ) {
            dao.update("comm.base.calendarMng.updateComId", eachRow);
        }
    }
    
    /**
     * @methodName  : selectMonthWeekendList
     * @date        : 2021.06.07
     * @desc        : 특정 기간 및 월에 대한 휴일 목록 조회
     * @param paramMap
     * @return
     */
    public List<CommMenuVo> selectWorkCaldAll(HashMap<String, Object> params) {
        return dao.selectList("comm.base.calendarMng.selectWorkcaldAll", params);
    }
    
    /**
     * @methodName  : selectWorkCaldDetl
     * @date        : 2022.05.11
     * @desc        : 상세 일정 조회
     * @param paramMap
     * @return
     */
	public List<CommMenuVo> selectWorkCaldDetl(HashMap<String, Object> params) {
		return dao.selectList("comm.base.calendarMng.selectWorkCaldDetl", params);
	}

	public List<CommMenuVo> selectWorkCaldDetlOne(HashMap<String, Object> params) {
		return dao.selectList("comm.base.calendarMng.selectWorkCaldDetlOne", params);
	}

	public void savePlanDetl(List<WorkCaldVo> ds) {
		for(WorkCaldVo eachRow : ds ) {
            dao.update("comm.base.calendarMng.savePlanDetl", eachRow);
        }		
	}

	public void updatePlanDetl(List<WorkCaldVo> ds) {
		for(WorkCaldVo eachRow : ds ) {
            dao.update("comm.base.calendarMng.updatePlanDetl", eachRow);
        }
	}

	public void deletePlanDetl(List<WorkCaldVo> ds) {
		for(WorkCaldVo eachRow : ds ) {
            dao.update("comm.base.calendarMng.deletePlanDetl", eachRow);
        }
	}

	public Object selectWeekWorkCald(HashMap<String, Object> params) {
		return dao.selectList("comm.base.calendarMng.selectWeekWorkCald", params);
	}

	public Object selectWeekWorkCaldDetl(HashMap<String, Object> params) {
		return dao.selectList("comm.base.calendarMng.selectWeekWorkCaldDetl", params);
	}
}
