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
import com.lsitc.fems.comm.auth.vo.RoleUserVo;
import com.lsitc.fems.comm.auth.vo.UserInfoVo;
import com.lsitc.fems.comm.auth.vo.UserVo;

@Service
public class UserRoleMngSvc extends BaseSvc {
    
    /**
     * @methodName  : selectRoleUser
     * @date        : 2021.04.26
     * @desc        : 역할별 사용자 조회
     * @param param
     * @return
     */
    public List<RoleUserVo> selectRoleUser(HashMap<String, Object> param){
        return dao.selectList("comm.auth.userRoleMng.selectRoleUser", param);
    }

    /**
     * @methodName  : selectUnAsgnRoleByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 미배정 역할 조회
     * @param params
     * @return
     */
    public Object selectUnAsgnRoleByUser(HashMap<String, Object> param) {
        return dao.selectList("comm.auth.userRoleMng.selectUnAsgnRoleByUser", param);
    }

    /**
     * @methodName  : selectAsgnRoleByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 역할 조회
     * @param params
     * @return
     */
    public Object selectAsgnRoleByUser(HashMap<String, Object> param) {
        return dao.selectList("comm.auth.userRoleMng.selectAsgnRoleByUser", param);
    }

    /**
     * @methodName  : selectAsgnMenuByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 메뉴 조회
     * @param params
     * @return
     */
    public Object selectAsgnMenuByUser(HashMap<String, Object> param) {
        return dao.selectList("comm.auth.userRoleMng.selectAsgnMenuByUser", param);
    }
    
    /**
     * @methodName  : selectUser
     * @date        : 2021.04.23
     * @desc        : 사용자를 조회한다.  
     * @param param
     * @return
     */
    public List<UserVo> selectUser(HashMap<String, Object> param){
        return dao.selectList("comm.auth.userRoleMng.selectUser", param);
    }
    
    /**
     * @methodName  : saveRoleUser
     * @date        : 2021.04.26
     * @desc        : 역할별 사용자 저장
     * @param roleUserList
     */
    @Transactional
    public void saveRoleUser(List<RoleUserVo> roleUserList) {
        for(RoleUserVo eachRow : roleUserList ) {
            switch (eachRow.getRowStat()) {
                case RowType.INSERT :
                    dao.insert("comm.auth.userRoleMng.insertRoleUser", eachRow);
                    break;
                case RowType.UPDATE :
                    dao.update("comm.auth.userRoleMng.updateRoleUser", eachRow);
                    break;
                case RowType.DELETE :
                    dao.delete("comm.auth.userRoleMng.deleteRoleUser", eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
    
    //향후 user로 분리가능성 있음.----------------------------------------------
    public Object selectUserPop(HashMap<String, Object> params) {
        return dao.selectList("comm.auth.userRoleMng.selectUserPop", params);
    }
    
    /**
     * @methodName  : saveUser
     * @date        : 2021.04.23
     * @desc        : 사용자를 저장한다.
     * @param userList
     */
    @Transactional
    public void saveUser(List<UserInfoVo> userList) {
        for(UserInfoVo eachRow : userList ) {
        	//암호화(최초로 비밀번호 입력 or 비밀번호를 변경했을 경우)
//        	if(eachRow.getUserPswd() != null) {
//	        	String  userPswdCrypted= CryptoUtils.getSHA512(eachRow.getUserPswd().toString());
//	        	eachRow.setUserPswd(userPswdCrypted);
//        	}
            switch (eachRow.getRowStat()) {
                case RowType.INSERT:
                    //사용자 추가
                    dao.insert("comm.auth.userRoleMng.insertUser", eachRow);
                    //FIXME 로그인 정보 추가(이 기능은 풀어야할지.. 한다면 어떻게 값을 받을지?
                    insertLoginInfo(eachRow);
                    break;
                case RowType.UPDATE:
                    //사용자 update
                    dao.update("comm.auth.userRoleMng.updateUser", eachRow);
                    //로그인 정보 업데이트
                    updateLoginInfo(eachRow);
                    break;
                case RowType.DELETE:
                    //사용자 정보 업데이트
                    dao.update("comm.auth.userRoleMng.deleteUser", eachRow);
                    //로그인정보 삭제
                    deleteLoginInfo(eachRow);
                    break;
                default : 
                    break;
            }
        }
    }
    
    /**
     * @methodName  : insertLoginInfo
     * @date        : 2021.04.23
     * @desc        : 로그인정보 추가
     * @param loginInfo
     */
    public void insertLoginInfo(UserInfoVo loginInfo) {
        //비밀번호 암호화(단방향)
//        loginInfo.setUserPswd(CryptoUtils.getSHA512(loginInfo.getUserPswd()));
        dao.insert("comm.auth.loginInfo.insertLoginInfo", loginInfo);
    }
    
    /**
     * @methodName  : updateLoginInfo
     * @date        : 2021.04.23
     * @desc        : 로그인정보 수정
     * @param loginInfo
     */
    public void updateLoginInfo(UserInfoVo loginInfo) {
        dao.update("comm.auth.loginInfo.updateLoginInfo", loginInfo);
    }

    /**
     * 
     * @methodName  : deleteLoginInfo
     * @date        : 2021.04.23
     * @desc        : 로그인정보 삭제
     * @param loginInfo
     */
    public void deleteLoginInfo(UserInfoVo loginInfo) {
        dao.delete("comm.auth.loginInfo.deleteLoginInfo", loginInfo);
    }

	public Object selectComparePswd(HashMap<String, Object> params) {
		return dao.selectList("comm.auth.userRoleMng.selectComparePswd", params);
	}

	public void updatePswd(List<UserInfoVo> userList) {
		for(UserInfoVo eachRow : userList ) {
			dao.update("comm.auth.userRoleMng.updatePswd", eachRow);
		}
	}
}
