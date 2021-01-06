package vip.wangjc.log.builder.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.log.builder.formatter.abstracts.AbstractLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;
import vip.wangjc.log.util.LogUtil;

/**
 * 默认综合日志格式化实现
 * @author wangjc
 * @title: DefaultLogFilterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 15:31
 */
public class DefaultLogFormatterBuilder extends AbstractLogFormatterBuilder {

    private final static Logger logger = LoggerFactory.getLogger(DefaultLogFormatterBuilder.class);

    @Override
    public void format(LogLevel logLevel, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter, Object result) {

        StringBuffer buffer = this.createInfoBuilder(name, entity);

        buffer.insert(0,"全局日志——");
        buffer.append("接收参数: [");
        buffer.append(LogUtil.getParamMap(entity.getParamNames(), args, paramNamesFilter));
        buffer.append("],");
        buffer.append("返回结果: [");
        buffer.append(LogUtil.parseParam(result));
        buffer.append("]");

        this.print(logger, logLevel, buffer.toString());
    }

    @Override
    public void format(String name, LogMethodEntity entity, Throwable throwable) {

        StringBuffer buffer = createInfoBuilder(name, entity);

        buffer.insert(0,"全局日志——");
        buffer.append("异常信息：[");
        buffer.append(throwable.getLocalizedMessage());
        buffer.append("]");

        this.print(logger, buffer.toString(), throwable);
    }
}
