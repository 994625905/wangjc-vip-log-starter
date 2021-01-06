package vip.wangjc.log.annotation;

import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.DefaultLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogPosition;

import java.lang.annotation.*;

/**
 * 综合日志，打印：参数+结果+异常
 * @author wangjc
 * @title: Log
 * @projectName wangjc-vip
 * @date 2021/1/4 - 14:58
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志名称
     * @return
     */
    String name();

    /**
     * 日志级别默认为info
     * @return
     */
    LogLevel level() default LogLevel.info;

    /**
     * 代码定位支持
     * @return
     */
    LogPosition position() default LogPosition.ON;

    /**
     * 参数过滤
     * @return
     */
    String[] paramsFilter() default {};

    /**
     * 日志格式化的构建器
     * @return
     */
    Class<? extends AbstractLogFormatterBuilder> formatter() default DefaultLogFormatterBuilder.class;

    /**
     * 日志的回调构建器
     * @return
     */
    Class<? extends LogCallbackBuilder> callback() default DefaultLogCallbackBuilder.class;
}
