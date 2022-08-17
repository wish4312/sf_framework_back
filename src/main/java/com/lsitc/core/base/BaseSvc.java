package com.lsitc.core.base;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lsitc.core.dao.CommonDao;

/**
 * BaseSvc
 * @desc : servcice에 dao를 넣어주기 위한 객체로 모든 service는 이를 상속받아 사용한다.
 */
public class BaseSvc {
    
    @Resource(name = "commonDao")
    protected CommonDao dao;
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public class RowType {
        public static final String INSERT = "I";
        public static final String UPDATE = "U";
        public static final String DELETE = "D";
    }
}
