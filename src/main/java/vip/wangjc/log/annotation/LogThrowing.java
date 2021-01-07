package vip.wangjc.log.annotation;

import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.DefaultThrowingLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractThrowingLogFormatterBuilder;

import java.lang.annotation.*;

/**
 * 异常日志
 * @author wangjc
 * @title: LogThrowing
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 10:25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogThrowing {

    /**
     * 业务名称
     * @return
     */
    String name();

    /**
     * 日志格式化的构建器
     * @return
     */
    Class<? extends AbstractThrowingLogFormatterBuilder> formatter() default DefaultThrowingLogFormatterBuilder.class;

    /**
     * 回调构建器
     * @return
     */
    Class<? extends LogCallbackBuilder> callback() default DefaultLogCallbackBuilder.class;
}
