package com.lsitc.global.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.lsitc.global.common.BaseVo;
import com.lsitc.global.common.SessionVo;
import com.lsitc.global.util.JwtTokenUtils;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
                BoundSql.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class DefaultInterceptor implements Interceptor {
    /**
     * @methodName  : intercept
     * @date        : 2021.02.19
     * @desc        : mybatis에서 sql 수행 시 수행되는 interceptor로, session객체를 추가해준다.
     *                (참조 :: https://developpaper.com/mybatis-interceptor/)
     * @param invocation
     * @return
     * @throws Throwable
     */ 
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
	    //mybatis mapping params
		Object parameter = invocation.getArgs()[1];
		
		//세션객체
		SessionVo sessionVo = JwtTokenUtils.getSessionVo();
		
		if( parameter instanceof Map || parameter instanceof HashMap ) {
		    //map일때
			Map<String, Object> map = (Map<String, Object>) parameter;
			map.put("session", sessionVo);
		} else if ( parameter instanceof BaseVo ) {
		    //baseVo일때
			BaseVo<?> vo = (BaseVo<?>) parameter;
			vo.setSession(sessionVo);
		}
		
		//바인딩된 파라미터를 바탕으로 수행
		return invocation.proceed();
	}
}
