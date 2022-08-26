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


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.domain.base.vo.ComIdVo;


@Service
public class ComIdSvc extends BaseSvc {

    /**
     * @methodName  : selectComId
     * @date        : 2021.04.23
     * @desc        : 회사코드 조회
     * @param paramMap
     * @return
     */
    public List<ComIdVo> selectComId(Map<String, Object> map){
        return dao.selectList("comm.base.comId.selectComId", map);
    }
    
    /**
     * 
     * @methodName  : saveComId
     * @date        : 2021.04.23
     * @desc        : 회사코드 저장
     * @param paramMap
     * @return
     */
    @Transactional
    public void saveComId(List<ComIdVo> comIdList) {
        for(ComIdVo eachRow : comIdList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    dao.insert("comm.base.comId.insertComId", eachRow);
                    break;
                case RowType.UPDATE:
                    dao.update("comm.base.comId.updateComId", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.base.comId.deleteComId", eachRow);
                    break;
            }
        }
    }
}