package vip.wangjc.log.annotation;

import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.DefaultResultLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractResultLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogPosition;

import java.lang.annotation.*;

/**
 * 结果日志
 * @author wangjc
 * @title: LogResult
 * @projectName wangjc-vip
 * @date 2021/1/4 - 20:02
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogResult {

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
     * 参数日志格式化的构建器
     * @return
     */
    Class<? extends AbstractResultLogFormatterBuilder> formatter() default DefaultResultLogFormatterBuilder.class;

    /**
     * 日志的回调构建器
     * @return
     */
    Class<? extends LogCallbackBuilder> callback() default DefaultLogCallbackBuilder.class;
}
