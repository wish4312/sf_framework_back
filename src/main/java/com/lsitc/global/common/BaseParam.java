package com.lsitc.global.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lsitc.global.util.CommonUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * BaseParam 
 * @desc : Save 시 Request를 변환을 위한 객체
 */
public class BaseParam {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Setter
    private HashMap<String, Object> params;
    @Getter @Setter
    private HashMap<String, List<Map<String, Object>>> datas;
    
    /**
     * @methodName  : getParam
     * @date        : 2021.04.22
     * @desc        : POST로 넘어온 params중 nm인것을 반환한다. 없을 때 null
     * @param nm
     * @return
     */
    public Object getParam(String nm) {
        return params.get(nm);
    }
    
    /**
     * @methodName  : getParams
     * @date        : 2021.04.22
     * @desc        : 파라미터를 넘긴다. 없으면 빈 hashMap을 반환한다.
     * @return
     */
    public HashMap<String, Object> getParams() {
        if( null == params ) { 
            return new HashMap<String, Object>();
        } else {
            return params;
        }
    }
    
    /**
     * @methodName  : get
     * @date        : 2021.04.22
     * @desc        : POST로 넘어온 Dataset중 dsNm인것을 Map으로 반환한다.
     * @param dsNm
     * @return
     */
    public List<Map<String, Object>> getDs(String dsNm) {
        return datas.get(dsNm);
    }
    
    /**
     * 
     * @methodName  : get
     * @date        : 2021.04.22
     * @desc        : POST로 넘어온 Dataset중 dsNm인것을 넘겨준 class의 형태로 반환한다.
     * @param <T>
     * @param dsNm
     * @param clzz
     * @return
     */
    public <T> List<T> getDs(String dsNm, Class<T> clzz) {
        return CommonUtils.convertListMapToListObject(datas.get(dsNm), clzz);
    }
    
    /**
     * @methodName  : getDataNames
     * @date        : 2021.04.22
     * @desc        : POST로 넘어온 Dataset중 dsNm인것을 넘겨준다.
     * @return
     */
    public String[] getDsNames() {
        return datas.keySet().toArray(new String[datas.size()]);
    }
//  private Map<String, BaseRows> datas;
//    public <T> List<T> getInsert(String dsNm, Class<T> clzz) {
//        return CommonUtils.convertListMapToListObject(datas.get(dsNm).getInsert(), clzz); 
//    }
//    
//    public <T> List<T> getUpdate(String dsNm, Class<T> clzz) {
//        return CommonUtils.convertListMapToListObject(datas.get(dsNm).getUpdate(), clzz); 
//    }
//
//    public <T> List<T> getDelete(String dsNm, Class<T> clzz) {
//        return CommonUtils.convertListMapToListObject(datas.get(dsNm).getDelete(), clzz); 
//    }
}
