package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Controller 에노테이션
@Target({ElementType.TYPE}) // 클래스 타입
@Retention(RetentionPolicy.RUNTIME) // 유지기간 => 런타입
public @interface Controller {

}
