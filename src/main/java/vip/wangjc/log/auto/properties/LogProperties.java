package vip.wangjc.log.auto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import vip.wangjc.log.builder.callback.DefaultLogCallbackBuilder;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.DefaultLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogPosition;

/**
 * 全局：综合日志的配置文件
 * @author wangjc
 * @title: LogProperties
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 10:22
 */
@ConfigurationProperties(prefix = "vip.wangjc.log.around")
public class LogProperties {

    /**
     * 全局：综合日志级别：默认info
     */
    private LogLevel level = LogLevel.info;

    /**
     * 全局：综合日志的代码定位
     */
    private LogPosition position = LogPosition.ON;

    /**
     * 全局：综合日志的格式化构建器
     */
    private Class<? extends AbstractLogFormatterBuilder> formatter = DefaultLogFormatterBuilder.class;

    /**
     * 全局：综合日志的回调构建器
     */
    private Class<? extends LogCallbackBuilder> callback = DefaultLogCallbackBuilder.class;

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public LogPosition getPosition() {
        return position;
    }

    public void setPosition(LogPosition position) {
        this.position = position;
    }

    public Class<? extends AbstractLogFormatterBuilder> getFormatter() {
        return formatter;
    }

    public void setFormatter(Class<? extends AbstractLogFormatterBuilder> formatter) {
        this.formatter = formatter;
    }

    public Class<? extends LogCallbackBuilder> getCallback() {
        return callback;
    }

    public void setCallback(Class<? extends LogCallbackBuilder> callback) {
        this.callback = callback;
    }
}
