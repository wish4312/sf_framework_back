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
package com.lsitc.fems.comm.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.core.base.BaseSvc;
import com.lsitc.fems.comm.base.vo.WidgetVo;

@Service
public class WidgetMngSvc extends BaseSvc {

    /**
     * @methodName  : selectWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param param
     * @return
     */
    public List<WidgetVo> selectWidget(HashMap<String, Object> param){
        return dao.selectList("comm.base.widgetMng.selectWidget", param);
    }
    
    /**
     * @methodName  : saveWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 저장
     * @param prgmList
     */
    @Transactional
    public void saveWidget(List<WidgetVo> prgmList) {
        for(WidgetVo eachRow : prgmList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    dao.insert("comm.base.widgetMng.insertWidget", eachRow);
                    break;
                case RowType.UPDATE:
                    dao.update("comm.base.widgetMng.updateWidget", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.base.widgetMng.deleteWidget", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }

    /**
     * @methodName  : selectNotMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param param
     * @return
     */
	public Object selectNotMyWidget(HashMap<String, Object> params) {
		return dao.selectList("comm.base.widgetMng.selectNotMyWidget", params);
	}
	
    /**
     * @methodName  : selectMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param param
     * @return
     */
	public Object selectMyWidget(HashMap<String, Object> params) {
		return dao.selectList("comm.base.widgetMng.selectMyWidget", params);
	}
	
	
    /**
     * @methodName  : saveMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 저장
     * @param prgmList
     */
    @Transactional
    public void saveMyWidget(List<WidgetVo> prgmList) {
        for(WidgetVo eachRow : prgmList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    dao.insert("comm.base.widgetMng.insertMyWidget", eachRow);
                    break;
                case RowType.UPDATE:
                    dao.update("comm.base.widgetMng.updateMyWidget", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.base.widgetMng.deleteMyWidget", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }

	public Object selectWidgetPrgmUrl(HashMap<String, Object> params) {
		return dao.selectList("comm.base.widgetMng.selectWidgetPrgmUrl", params);
	}
	
}