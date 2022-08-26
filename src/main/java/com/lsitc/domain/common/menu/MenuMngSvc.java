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
package com.lsitc.domain.common.menu;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.domain.auth.vo.CommMenuVo;


@Service
public class MenuMngSvc extends BaseSvc {
    /**
     * @methodName  : selectMenu
     * @date        : 2021.06.07
     * @desc        : 메뉴 조회
     * @param paramMap
     * @return
     */
    public List<MenuVo> selectMenu(HashMap<String, Object> param){
        return dao.selectList("comm.base.menuMng.selectCommMenuTree", param);
    }

    /**
     * @methodName  : saveMenu
     * @date        : 2021.06.07
     * @desc        : 메뉴 저장
     * @param paramMap
     * @return
     */
    @Transactional
    public void saveMenu(List<MenuVo> menuList) {
        for(MenuVo eachRow : menuList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    dao.insert("comm.base.menuMng.insertMenu", eachRow);
                    break;
                case RowType.UPDATE:
                    dao.update("comm.base.menuMng.updateMenu", eachRow);
                    break;
                case RowType.DELETE:
                    dao.delete("comm.base.menuMng.deleteMenu", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
    
    /**
     * @methodName  : selectMenuPop
     * @date        : 2021.06.07
     * @desc        : (팝업)메뉴 조회
     * @param paramMap
     * @return
     */
    public Object selectMenuPop(HashMap<String, Object> params) {
        return dao.selectList("comm.base.menuMng.selectMenuPop", params);
    }
    
    
    //FIXME 이거 수정할 것!
    public List<CommMenuVo> selectAuthMenu() {
        return dao.selectList("comm.base.menuMng.selectAuthMenu", new HashMap<String, Object>());
    }
  //FIXME 이거 수정할 것!
    public List<CommMenuVo> selectAuthMenu(String userId) {
        //FIXME ???
        //최초 로그인 시에는 session이 없어 userID를 넣는다.. 
        return dao.selectList("comm.base.menuMng.selectAuthMenu", userId);
    }

	public void saveBookmark(List<MenuVo> ds) {
		for(MenuVo eachRow : ds ) {
            dao.update("comm.base.menuMng.saveBookmark", eachRow);
        }
	}

	public void deleteBookmark(List<MenuVo> ds) {
		for(MenuVo eachRow : ds ) {
            dao.update("comm.base.menuMng.deleteBookmark", eachRow);
        }
	}

	public List<CommMenuVo> selectMyMenu(HashMap<String, Object> params) {
		return dao.selectList("comm.base.menuMng.selectMyMenu", params);
	}
}