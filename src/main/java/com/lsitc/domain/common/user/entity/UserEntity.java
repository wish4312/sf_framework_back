package com.lsitc.domain.common.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.lsitc.global.common.BaseAbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseAbstractEntity implements UserDetails {

  private static final long serialVersionUID = 7428290075155619330L;
  
  private Long id;
  private String userId;
  private String password;
  private String name;
  private String email;
  private String phoneNumber;
  private boolean isDeleted;
  private Long deletedBy;
  private LocalDateTime deletedDate;

  @Builder
  public UserEntity(Long id, String userId, String password, String name, String email,
      String phoneNumber, boolean isDeleted, Long deletedBy,
      LocalDateTime deletedDate) {
    this.id = id;
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.isDeleted = isDeleted;
    this.deletedBy = deletedBy;
    this.deletedDate = deletedDate;
  }

  public void delete() {
    this.isDeleted = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // FIXME 권한 관련 로직이 들어가는 곳
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }
  
  @Override
  public String getPassword() {
      return this.password;
  }

  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !this.isDeleted;
  }
}
