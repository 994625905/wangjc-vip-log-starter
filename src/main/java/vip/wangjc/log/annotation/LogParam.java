package vip.wangjc.log.annotation;

import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.DefaultParamLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractParamLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogPosition;

import java.lang.annotation.*;

/**
 * 参数日志
 * @author wangjc
 * @title: LogParam
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 19:53
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogParam {

    /**
     * 业务名称
     * @return
     */
    String name();

    /**
     * 日志级别
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
    String[] paramFilter() default {};

    /**
     * 参数日志格式化的构建器
     * @return
     */
    Class<? extends AbstractParamLogFormatterBuilder> formatter() default DefaultParamLogFormatterBuilder.class;

    /**
     * 日志的回调构建器
     * @return
     */
    Class<? extends LogCallbackBuilder> callback() default DefaultLogCallbackBuilder.class;
}
