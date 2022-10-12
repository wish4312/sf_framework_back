package com.lsitc.global.mybatis;

import com.lsitc.global.auditing.Auditable;
import com.lsitc.global.auditing.CurrentDateTimeProvider;
import com.lsitc.global.auditing.CurrentUserInfoProvider;
import com.lsitc.global.auditing.DateTimeProvider;
import com.lsitc.global.auditing.SoftDeletable;
import com.lsitc.global.auditing.UserProvider;
import com.lsitc.global.common.BaseVo;
import com.lsitc.global.common.SessionVo;
import com.lsitc.global.util.JwtTokenUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
@Intercepts({
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
            CacheKey.class, BoundSql.class}),
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class})
})
public class DefaultInterceptor implements Interceptor {

  /**
   * @param invocation
   * @return
   * @throws Throwable
   * @methodName : intercept
   * @date : 2021.02.19
   * @desc : mybatis에서 sql 수행 시 수행되는 interceptor로, session객체를 추가해준다. (참조 ::
   * https://developpaper.com/mybatis-interceptor/)
   */
  private DateTimeProvider dateTimeProvider = CurrentDateTimeProvider.INSTANCE;
  private UserProvider userProvider = CurrentUserInfoProvider.INSTANCE;

  @SuppressWarnings("unchecked")
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    //mybatis mapping params
    Object parameter = invocation.getArgs()[1];

    //세션객체
    SessionVo sessionVo = JwtTokenUtils.getSessionVo();

    if (parameter instanceof Auditable) {
      Auditable baseAbstractEntity = (Auditable) parameter;
      if ("update".equals(invocation.getMethod().getName())) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
          baseAbstractEntity.setCreatedBy(userProvider.getId());
          baseAbstractEntity.setCreatedDate(dateTimeProvider.getNow());
          baseAbstractEntity.setLastModifiedBy(userProvider.getId());
          baseAbstractEntity.setLastModifiedDate(dateTimeProvider.getNow());
        } else if (mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
          baseAbstractEntity.setLastModifiedBy(userProvider.getId());
          baseAbstractEntity.setLastModifiedDate(dateTimeProvider.getNow());

          if (parameter instanceof SoftDeletable) {
            SoftDeletable softDeletableEntity = (SoftDeletable) parameter;
            if (softDeletableEntity.isDeleted()) {
              softDeletableEntity.setDeletedBy(userProvider.getId());
              softDeletableEntity.setDeletedDate(dateTimeProvider.getNow());
            }
          }
        }
      }
    } else if (parameter instanceof Map || parameter instanceof HashMap) {
      //map일때
      Map<String, Object> map = (Map<String, Object>) parameter;
      map.put("session", sessionVo);
    } else if (parameter instanceof BaseVo) {
      //baseVo일때
      BaseVo<?> vo = (BaseVo<?>) parameter;
      vo.setSession(sessionVo);
    }

    //바인딩된 파라미터를 바탕으로 수행
    return invocation.proceed();
  }
}
