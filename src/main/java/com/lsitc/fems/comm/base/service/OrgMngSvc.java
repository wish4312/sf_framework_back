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
import com.lsitc.fems.comm.base.vo.DeptCdVo;

@Service
public class OrgMngSvc extends BaseSvc {
    
    /**
     * @methodName  : selectDeptCd
     * @date        : 2021.06.07
     * @desc        : 부서코드 조회
     * @param paramMap
     * @return
     */
    public List<DeptCdVo> selectDeptCd(HashMap<String, Object> param){
        return dao.selectList("comm.base.orgMng.selectDeptCd", param);
    }
    
    /**
     * @methodName  : saveDeptCd
     * @date        : 2021.06.07
     * @desc        : 부서코드 저장
     * @param paramMap
     * @return
     */
    @Transactional
    public void saveDeptCd(List<DeptCdVo> deptCdList) {
        for(DeptCdVo eachRow : deptCdList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    dao.insert("comm.base.orgMng.insertDeptCd", eachRow);
                    break;
                case RowType.UPDATE:
                    dao.update("comm.base.orgMng.updateDeptCd", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.base.orgMng.deleteDeptCd", eachRow);
                    break;
            }
        }
    }
}