package com.lsitc.core.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * BaseVo
 * @desc : 시스템 컬럼 등 공통기능을 제공하기 위한 객체로 모든 VO는 이를 상속한다. 
 * @param <T> : Tree형태로 변환하기 위한 Class 객체
 */
@Getter
@Setter
public class BaseVo<T> {
    //행상태
    private String rowStat;
    //시스템 컬럼
    private String regUserNo;
    private Date regDttm;
    private String procUserNo;
    private Date procDttm;
    //계층구조를 만들어주기 위한 객체
	private List<T> childeVo = new ArrayList<T>();
	//세션정보를 담아주기 위한 객체
	private SessionVo session = null;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
