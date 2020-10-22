package tech.guyi.web.quick.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 授权注解
 * 使用此注解标识需要拦截请求
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
public @interface Authorization {

    /**
     * 默认类型名称
     */
    String DEFAULT_TYPE = "default";

    /**
     * 拦截类型名称
     * 用于区分使用不同的拦截器
     * @return 类型名称
     */
    String[] type() default {DEFAULT_TYPE};

}
