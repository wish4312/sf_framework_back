package com.lsitc.core.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * BaseResponse
 * @desc: API return 시 기본 포멧을 지정하고, 추가하여 보낼 수 있도록 하는 객체
 */
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 3973097367004179053L;
    
    @Getter@Setter
    private int retnCd = 0;
    @Getter@Setter
    private String retnMsg = "";
    @Getter
    private Map<String, Object> dataset;

    /**
     * 
     * @methodName  : add
     * @date        : 2021.02.19
     * @desc        : API return시 반환하고자 하는 객체를 추가한다.
     * @param key   : 반환하고자 하는 객체의 key값
     * @param value : 반환하고자 하는 객체
     */
    public void add(String key, Object value) {
        if ( null == this.dataset ) {
            this.dataset = new HashMap<String, Object>();
        }
        this.dataset.put(key, value);
    }

    /**
     * 
     * @methodName  : remove
     * @date        : 2021.02.19
     * @desc        : 이미 추가된 객체를 제거한다.
     * @param key   : 삭제할 객체의 key값
     */
    public void remove(String key) { 
        if ( null != this.dataset ) {
            this.dataset.remove(key);
        }
    }

    /**
     * 
     * @methodName  : get
     * @date        : 2021.02.19
     * @desc        : key에 매핑된 객체를 반환한다.
     * @param key   : 반환하고자 하는 객체의 key값
     * @return
     */
    public Object get(String key) {
        if ( null != this.dataset ) {
            return this.dataset.get(key);
        } else {
            return null;
        }            
    }
}