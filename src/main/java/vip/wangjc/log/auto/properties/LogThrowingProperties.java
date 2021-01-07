package vip.wangjc.log.auto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import vip.wangjc.log.builder.formatter.DefaultThrowingLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractThrowingLogFormatterBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;

/**
 * 异常日志的配置文件
 * @author wangjc
 * @title: LogProperties
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 10:22
 */
@ConfigurationProperties(prefix = "vip.wangjc.log.throw")
public class LogThrowingProperties {

    /**
     * 异常日志格式化的构建器
     */
    private Class<? extends AbstractThrowingLogFormatterBuilder> formatter = DefaultThrowingLogFormatterBuilder.class;

    /**
     * 异常日志的回调构建器
     */
    private Class<? extends LogCallbackBuilder> callback = DefaultLogCallbackBuilder.class;

    public Class<? extends AbstractThrowingLogFormatterBuilder> getFormatter() {
        return formatter;
    }

    public void setFormatter(Class<? extends AbstractThrowingLogFormatterBuilder> formatter) {
        this.formatter = formatter;
    }

    public Class<? extends LogCallbackBuilder> getCallback() {
        return callback;
    }

    public void setCallback(Class<? extends LogCallbackBuilder> callback) {
        this.callback = callback;
    }
}
