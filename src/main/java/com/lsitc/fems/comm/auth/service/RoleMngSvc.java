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
package com.lsitc.fems.comm.auth.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.fems.comm.auth.vo.RoleVo;

@Service
public class RoleMngSvc extends BaseSvc {

    /**
     * @methodName  : selectRole
     * @date        : 2021.04.26
     * @desc        : 역할조회
     * @param param
     * @return
     */
    public List<RoleVo> selectRole(HashMap<String, Object> param){
        return dao.selectList("comm.auth.roleMng.selectRole", param);
    }
    
    /**
     * @methodName  : saveRole
     * @date        : 2021.04.26
     * @desc        : 역할저장
     * @param roleList
     */
    @Transactional
    public void saveRole(List<RoleVo> roleList) {
        for(RoleVo eachRow : roleList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT :
                    dao.insert("comm.auth.roleMng.insertRole", eachRow);
                    break;
                case RowType.UPDATE :
                    dao.update("comm.auth.roleMng.updateRole", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.auth.roleMng.deleteRole", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
}