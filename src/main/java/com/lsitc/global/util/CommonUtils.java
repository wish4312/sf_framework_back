package com.lsitc.global.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.lsitc.global.error.exception.BisiExcp;

public class CommonUtils {
    //날짜시간포멧
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * @methodName  : convertListMapToListObject
     * @date        : 2021.02.19
     * @desc        : List<Map>을 List<Object>로 변환한다.
     * @param list  : 변환될 List<Map>
     * @param clazz : 반환될 Object의 클래스
     * @return
     */
    public static <T extends Map<String, Object>, C> List<C> convertListMapToListObject(
        List<T> list, Class<C> clazz) {
        List<C> beanList = new ArrayList<C>();
        for (Map<String, Object> source : list) {
            C bean = convertMapToObject(source, clazz);
            beanList.add(bean);
        }
        return beanList;
    }
    
    /**
     * @methodName  : convertMapToObject
     * @date        : 2021.02.19
     * @desc        : Map을 Object로 변환한다.
     * @param source : 변환될 Map
     * @param targetClass : 반환될 Object의 클래스
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <C> C convertMapToObject(Map<String, Object> source, Class<C> targetClass) {
        C bean = null;
        PropertyDescriptor[] targetPds = null;
        try {
            bean = targetClass.newInstance();
            targetPds = BeanUtils.getPropertyDescriptors(targetClass);
        } catch (InstantiationException | IllegalAccessException e) {
            new IllegalArgumentException("Cannot initiate class",e);
        }
            for (PropertyDescriptor desc : targetPds) {
                Object value = source.get(desc.getName());
                if (value != null) {
                    Method writeMethod = desc.getWriteMethod();
                    if (writeMethod != null) {
                        try {
                            //Map 형태를 변환 시 객체타입인 경우 문제를 방지하고자 함.
                            if ( desc.getPropertyType() == BigDecimal.class ) {
                                //BigDecimal일 때
                                if( StringUtils.isAllEmpty(String.valueOf(value)) ) {
                                    writeMethod.invoke(bean, new Object[] { new BigDecimal(0) } );
                                } else {
                                    writeMethod.invoke(bean, new Object[] { new BigDecimal(String.valueOf(value)) } );
                                }
                            } else if ( desc.getPropertyType() == Date.class ) {
                                //Date형태일 때 
                                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
                                if( value.getClass() == String.class && StringUtils.isNotEmpty(value.toString()) ) {
                                    try {
                                        writeMethod.invoke(bean, new Object[] { dateFormat.parse(value.toString())} );
                                    } catch (ParseException e) {
                                        dateFormat = new SimpleDateFormat(DATE_FORMAT);
                                        try {
                                            writeMethod.invoke(bean, new Object[] { dateFormat.parse(value.toString())} );
                                        } catch (ParseException e1) {
                                            writeMethod.invoke(bean, new Object[] { null } );
                                        }
                                    }
                                } else {
                                    writeMethod.invoke(bean, new Object[] { null } );
                                }
                            } else {
                                //이외 타입은 정상
                                writeMethod.invoke(bean, new Object[] { value });
                            }
                        } catch (InvocationTargetException e) {
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        } 
                    }
                }
            }
        return bean;
    }
    
    /**
     * @methodName  : convertObjecToMap
     * @date        : 2021.02.19
     * @desc        : Object을 Map으로 변환한다.
     * @param source
     * @return
     */
    public static Map<String, Object> convertObjecToMap(Object source) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(source.getClass());

            for (PropertyDescriptor desc : targetPds) {
                Object value = desc.getReadMethod().invoke(source);
                if (value != null) {
                    resultMap.put(desc.getName(), value);
                }
            }
        } catch (IllegalAccessException e) {
            new IllegalStateException("Cannot access the property",e);
        } catch (InvocationTargetException e) {
            new IllegalArgumentException(e);
        }
//        Field[] fields = source.getClass().getFields();
//        for(Field field : fields) {
//            String filedNm = field.getName();
//            System.out.println("filedNm : " + filedNm);
//            Method method;
//            try {
//                method = source.getClass().getMethod("get" + StringUtils.capitalize(filedNm));
//                resultMap.put(filedNm, method.invoke(source));
//            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                new BisiExcp("convertObjecToMap에서 에러 발생");
//            }
//        }
        return resultMap;
    }
    
    /**
     * @methodName  : convertListObjectToListMap
     * @date        : 2021.02.19
     * @desc        : List<Object>을 Map<String, Object>으로 변환한다.
     * @param source
     * @return
     */
    public static List<Map<String, Object>> convertListObjectToListMap(List<?> source) {
        List<Map<String, Object>> resultList = new ArrayList<>(); 
        
        for(Object eachRow : source) {
            resultList.add(convertObjecToMap(eachRow));
        }
        
        return resultList;
    }
	
    /**
     * @methodName  : buildIdMap
     * @date        : 2021.02.19
     * @desc        : tree를 만들기 위한 메소드..
     * @param targets
     * @param id
     * @return
     */
    public static <T> Map<String, T> buildIdMap(Collection<T> targets, String id){
        Map<String, T> result = new HashMap<String, T>();
        if(targets!=null && !targets.isEmpty()){
            final Iterator<T> iterator = targets.iterator();
            while (iterator.hasNext()){
                final T next = iterator.next();
                try {
	                Method method =  next.getClass().getMethod("get" + StringUtils.capitalize(id), null);
	                String idVal = method.invoke(next).toString();
	                
	                if(StringUtils.isNotBlank( idVal)){
	                    result.put(idVal,next);
	                }
                }catch(Exception e) {
                  //FIXME 다국어 처리
                  new BisiExcp("buildIdMap에서 에러 발생");
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @methodName  : getTree
     * @date        : 2021.02.19
     * @desc        : BaseObject를 상속한 객체는 id와 upId를 활용하여 Tree구조로 변경해준다.
     * @param <T>
     * @param all
     * @param id
     * @param upId
     * @return
     */
    public static <T>  List<T> getTree(List<T> all , String id, String upId){
        final List<T> result = new ArrayList<>();
        final Map<String, T> allMap = buildIdMap(all, id);
        final Iterator<T> iterator = all.iterator();
        while (iterator.hasNext()){        	
            final T next = iterator.next();
            
            try {
	        	Method methodId =  next.getClass().getMethod("get" + StringUtils.capitalize(id), null);
	        	Method methodUpId =  next.getClass().getMethod("get" + StringUtils.capitalize(upId), null);
	            String idVal = methodId.invoke(next).toString();
	            String upIdVal = methodUpId.invoke(next).toString();
	            
	            final String parentId = upIdVal;
	            if(StringUtils.isNotBlank(parentId)){
	                final T node = allMap.get(idVal);
	                final T nodeP = allMap.get(parentId);
	                if(nodeP != null){
	                	Method getChildeVo = nodeP.getClass().getMethod("getChildeVo", null);
	                	((List)getChildeVo.invoke(nodeP)).add(node);
	                }
	            }else{
	                result.add(next);
	            }
            }catch(Exception e){
                //FIXME 다국어 처리
                new BisiExcp("getTree에서 에러 발생");
            }
        }
        return result;
    }

    /**
     * @methodName  : buildIdMap ( List & Map )
     * @date        : 2021.02.19
     * @desc        : tree를 만들기 위한 메소드..
     * @param targets
     * @param id
     * @return
     */
    public static Map<String, Map<String, Object>> buildIdMap(List<Map<String, Object>> targets, String id){
        Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
        if(targets!=null && !targets.isEmpty()){
        	for(Map<String, Object> target: targets) {
                try {
	                String idVal = target.get(id).toString();
	                
	                if(StringUtils.isNotBlank( idVal) && !"ROOT".equals(idVal)){
	                    result.put(idVal, target);
	                }
                }catch(Exception e) {
                  //FIXME 다국어 처리
                  new BisiExcp("buildIdMap에서 에러 발생");
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @methodName  : getTreeMap
     * @date        : 2021.02.19
     * @desc        : Map List 의 id와 upId를 활용하여 Tree구조로 변경해준다.
     * @param <T>
     * @param all
     * @param id
     * @param upId
     * @return
     */
    public static List<Map<String, Object>> getTreeMap(List<Map<String, Object>> all , String id, String upId){
    	final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	final Map<String, Map<String, Object>> allMap = buildIdMap(all, id);
    	
    	for(Map<String, Object> next:  all) {
    		try {
	            String idVal = next.get(id).toString();
	            String upIdVal = next.get(upId).toString();
	            final String parentId = upIdVal;
	            if(StringUtils.isNotBlank(parentId) && !"ROOT".equals(parentId)){
	                final Map<String, Object> node = allMap.get(idVal);
	                final Map<String, Object> nodeP = allMap.get(parentId);
	                if(nodeP != null){
	                	if(nodeP.get("childeVo") == null) {
	                		nodeP.put("childe_vo", new ArrayList<Map<String, Object>>());
	                	}
	                	((List)nodeP.get("childeVo")).add(node);
	                }
	            }else{
	                result.add(next);
	            }
    			
    		}catch(Exception e){
                //FIXME 다국어 처리
                new BisiExcp("getTreeMap에서 에러 발생");
            }
    	}
        return result;
    }
    
    /**
     * 
     * @methodName  : splitByByte
     * @date        : 2021.02.22
     * @desc        : 문자열을 최대byte크기로 분할하여 반환한다.
     * @param source : 자를 문자열
     * @param maxLength : 자를 byte size
     * @return
     */
    public static String[] splitByByte(String source, int maxLength) {
        List<String> resultList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        char[] charArr = source.toCharArray();
        int byteLen = 0;
        int charLen = charArr.length;
        
        for(int charIdx=0; charIdx<charLen; charIdx++) {
            int eachCharByteLen = String.valueOf(charArr[charIdx]).getBytes().length;
            
            if(byteLen + eachCharByteLen <= maxLength) {
                //sb append
                sb.append(charArr[charIdx]);
                //add size
                byteLen += eachCharByteLen;
            } else {
                //add list
                resultList.add(sb.toString());
                //sb clear, clear size
                sb.setLength(0);
                byteLen = eachCharByteLen;
                //sb append
                sb.append(charArr[charIdx]);
            }
        }
        //add list
        resultList.add(sb.toString());
        
        return resultList.toArray(new String[resultList.size()]);
    }
    
    /**
     * @methodName  : getUUID
     * @date        : 2021.02.22
     * @desc        : random UUID 생성.
     * @return
     */
  	public static String getUUID() {
  		return UUID.randomUUID().toString();
  	}
}
