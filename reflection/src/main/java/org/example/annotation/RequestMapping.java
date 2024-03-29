package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// RequestMapping 에노테이션
@Target({ElementType.TYPE, ElementType.METHOD}) // 클래스, 메서드
@Retention(RetentionPolicy.RUNTIME) // 런타임동안 유지
public @interface RequestMapping {
    String value() default "";

    RequestMethod[] method() default {}; // enum
}
