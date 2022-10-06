package com.lsitc.domain.common.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.lsitc.domain.common.user.dao.UserDAO;
import com.lsitc.domain.common.user.entity.UserEntity;
import com.lsitc.domain.common.user.exception.UserException;
import com.lsitc.domain.common.user.vo.UserAddRequestVO;
import com.lsitc.domain.common.user.vo.UserAddResponseVO;
import com.lsitc.domain.common.user.vo.UserInfoGetRequestVO;
import com.lsitc.domain.common.user.vo.UserInfoGetResponseVO;
import com.lsitc.domain.common.user.vo.UserListGetResponseVO;
import com.lsitc.domain.common.user.vo.UserModifyRequestVO;
import com.lsitc.domain.common.user.vo.UserModifyResponseVO;
import com.lsitc.domain.common.user.vo.UserRemoveRequestVO;
import com.lsitc.domain.common.user.vo.UserRemoveResponseVO;
import com.lsitc.global.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserDAO userDAO;

  public UserInfoGetResponseVO getUserInfo(final UserInfoGetRequestVO userInfoGetRequestVO) {
    UserEntity userEntity = userInfoGetRequestVO.toEntity();
    log.info(userEntity.toString());
    UserEntity userInfo = userDAO.selectUserById(userEntity);
    return UserInfoGetResponseVO.of(userInfo);
  }

  public List<UserListGetResponseVO> getUserList() {
    List<UserEntity> userEntityList = userDAO.selectAll();
    return userEntityList.stream().map(UserListGetResponseVO::of).collect(Collectors.toList());
  }

  public UserAddResponseVO addUser(final UserAddRequestVO userAddRequestVO) {
    UserEntity userEntity = userAddRequestVO.toEntity();
    log.info(userEntity.toString());
    int addRows = userDAO.insertUser(userEntity); // userId가 UNIQUE KEY로 잡혀있어 중복되는 userId의 경우 에러 반환
    return UserAddResponseVO.of(addRows);
  }

  public UserModifyResponseVO modifyUser(final UserModifyRequestVO userModifyRequestVO) {
    UserEntity userEntity = userModifyRequestVO.toEntity();
    int upsertRows = upsertUser(userEntity);
    log.info(userEntity.toString());
    return UserModifyResponseVO.of(upsertRows);
  }

  private int upsertUser(UserEntity targetEntity) {
    UserEntity userEntity = userDAO.selectUserById(targetEntity);
    return userEntity != null ? userDAO.updateUserById(targetEntity)
        : userDAO.insertUserWithId(targetEntity);
  }

  // Delete시 flag 변경
  public UserRemoveResponseVO removeUser(final UserRemoveRequestVO userRemoveRequestVO)
      throws UserException {
    UserEntity userEntity = userDAO.selectUserById(userRemoveRequestVO.toEntity());
    if (userEntity == null) {
      throw new UserException();
    }
    userEntity.delete();
    log.info(userEntity.toString());
    int deleteRows = userDAO.updateUserIsDeletedById(userEntity);
    return UserRemoveResponseVO.of(deleteRows);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userInfo = userDAO.selectUserByUserId(username);
    
    if (userInfo == null) {
      throw new UsernameNotFoundException(username);
    }
    
    //// FIXME Jwt용 임시 정보 세팅 - 삭제예정 ==================================
    try {
      HashMap<String, Object> tmpTokenInfo = new HashMap<String, Object>();
      tmpTokenInfo.put("userNm", userInfo.getUsername());
      tmpTokenInfo.put("userNo", '3');
      tmpTokenInfo.put("blocId", "BL0001");
      tmpTokenInfo.put("comId", "FEMS");
      tmpTokenInfo.put("roleList", "ROL0001");
      tmpTokenInfo.put("blocNm", "1사업장");
      String token;
      token = JwtTokenUtils.createToken(tmpTokenInfo);
      //토큰을 쿠키에 셋팅
      HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
      JwtTokenUtils.setTokenOnResponse(token, response);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      return null;
    }
    //// ===================================================
    
    return userInfo;
  }
}
