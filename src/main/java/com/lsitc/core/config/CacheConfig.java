package com.lsitc.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * CacheConfig
 * @ desc : cache가 가능하도록 설정.
 */
@Configuration
@EnableCaching
public class CacheConfig {	
	@Autowired
	CacheManager cacheManager;
	
	/**
	 * @methodName  : evictAllCaches
	 * @date        : 2021.02.19
	 * @desc        : 모든 캐시된 정보를 지운다.
	 */
	public void evictAllCaches() {
		cacheManager.getCacheNames().stream()
		  .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	/**
	 * @methodName  : evictAllcachesAtIntervals
	 * @date        : 2021.02.19
	 * @desc        : 주기적으로 캐시를 비운다.(TTL 5분)
	 */
	//FIXME 캐시 주기를 정의하여 넣는다.
	@Scheduled(fixedRate = 5 * 60 * 1000) 
	public void evictAllcachesAtIntervals() {
        evictAllCaches();
	}
}
