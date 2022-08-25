package com.lsitc.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WithoutLoginCheck
 * @desc 해당 어노테이션이 있는 경우 로그인과 권한을 체크하지 않는다. 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutLoginCheck{
}