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
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;

@Service
public class MenuRoleMngSvc extends BaseSvc {
    
    /**
     * @methodName  : selectMenuRole
     * @date        : 2021.04.26
     * @desc        : 역할별메뉴 조회
     * @param param
     * @return
     */
    public List<?> selectMenuRole(HashMap<String, Object> param){
        return dao.selectList("comm.auth.menuRoleMng.selectMenuRole", param);
    }
    
    /**
     * @methodName  : selectunAsgnMenu
     * @date        : 2021.04.26
     * @desc        : 미배정메뉴 조회
     * @param param
     * @return
     */
    public List<?> selectUnAsgnMenu(HashMap<String, Object> param){
        return dao.selectList("comm.auth.menuRoleMng.selectUnAsgnMenu", param);
    }
    
    /**
     * @methodName  : saveMenuRole
     * @date        : 2021.04.26
     * @desc        : 역할별메뉴 저장
     * @param list
     */
    @Transactional
    public void saveMenuRole(List<Map<String, Object>> list) {
        String[] authCdArr = { "R", "C", "U", "D", "S", "E" };
        for(Map<String, Object> eachRow : list ) {
            if(RowType.DELETE.equals(eachRow.get("rowStat"))) { 
                //역할별메뉴권한 먼저 삭제
                dao.delete("comm.auth.menuRoleMng.deleteMenuRoleAuth", eachRow);
                dao.delete("comm.auth.menuRoleMng.deleteMenuRole", eachRow);
            } else if(RowType.UPDATE.equals(eachRow.get("rowStat"))) {
                //Merge문 실행
                dao.update("comm.auth.menuRoleMng.updateMenuRole", eachRow);
                dao.update("comm.auth.menuRoleMng.updateCommMenu",eachRow);

                //권한 추가
                for(int i=1,len=6; i<=len; i++) {
                    //각 컬럼별 권한을 셋팅
                    String authCd = eachRow.get("authCd"+i).toString();
                    
                    if("0".equals(authCd)) {
                        //'N'일 경우 삭제
                        eachRow.put("authCd", authCdArr[i-1]);
                        dao.delete("comm.auth.menuRoleMng.deleteMenuRoleAuth", eachRow);
                    } else {
                        //'Y'일 경우 추가
                        eachRow.put("authCd", authCdArr[i-1]);
                        dao.update("comm.auth.menuRoleMng.insertMenuRoleAuth", eachRow);
                    }
                }
            }
        }
    }
}
