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
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseSvc;
import com.lsitc.fems.comm.base.vo.CommCdVo;
import com.lsitc.fems.comm.base.vo.CommGrpCdVo;

@Service
public class CommCdMngSvc extends BaseSvc {

    /**
     * @methodName  : selectCommGrpCd
     * @date        : 2021.06.07
     * @desc        : 공통그룹코드 조회
     * @param paramMap
     * @return
     */
    public Object selectCommGrpCd(HashMap<String, Object> paramMap) {
        return dao.selectList("comm.base.commCdMng.selectCommGrpCd", paramMap);
    }

    /**
     * @methodName  : saveCommGrpCd
     * @date        : 2021.06.07
     * @desc        : 공통그룹코드 저장
     * @param paramMap
     * @return
     */
    public void saveCommGrpCd(List<CommGrpCdVo> grpCdList){
        for(CommGrpCdVo eachRow : grpCdList) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT :
                    dao.insert("comm.base.commCdMng.insertCommGrpCd", eachRow);
                    break;
                case RowType.UPDATE :
                    dao.update("comm.base.commCdMng.updateCommGrpCd", eachRow);
                    break;
                case RowType.DELETE :
                    dao.delete("comm.base.commCdMng.deleteCommGrpCd", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
    
    /**
     * @methodName  : selectCommCd
     * @date        : 2021.06.07
     * @desc        : 공통코드 조회
     * @param paramMap
     * @return
     */
    public List<CommCdVo> selectCommCd(BaseParam paramMap) {
        return dao.selectList("comm.base.commCdMng.selectCommCd", paramMap.getParams());
    }

    /**
     * @methodName  : saveCommCd
     * @date        : 2021.06.07
     * @desc        : 공통코드 저장
     * @param paramMap
     * @return
     */
    public void saveCommCd(List<CommCdVo> commCdList){ 
        for(CommCdVo eachRow : commCdList) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT :
                    dao.insert("comm.base.commCdMng.insertCommCd", eachRow);
                    break;
                case RowType.UPDATE :
                    dao.update("comm.base.commCdMng.updateCommCd", eachRow);
                    break;
                case RowType.DELETE :
                    dao.delete("comm.base.commCdMng.deleteCommCd", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
    
	/**
	 * @methodName  : selectGvno
	 * @date        : 2021.03.15
	 * @desc        : 채번API
	 * @param paramMap
	 * @return
	 */
    public String selectGvno(Map<String, Object> paramMap) {
        return dao.selectOne("comm.base.commCdMng.selectGvno", paramMap);
    }
    
}
