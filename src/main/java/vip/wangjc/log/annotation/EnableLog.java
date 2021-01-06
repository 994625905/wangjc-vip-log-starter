package vip.wangjc.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启wangjc-vip-log-starter的注解
 * @author wangjc
 * @title: EnableLog
 * @projectName wangjc-vip
 * @date 2021/1/5 - 10:10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableLog {


}
