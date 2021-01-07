package vip.wangjc.log.builder.formatter;

import vip.wangjc.log.builder.formatter.abstracts.AbstractLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;
import vip.wangjc.log.util.LogUtil;

/**
 * 默认综合日志格式化实现
 * @author wangjc
 * @title: DefaultLogFilterBuilder
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 15:31
 */
public class DefaultLogFormatterBuilder extends AbstractLogFormatterBuilder {

    @Override
    public void format(LogLevel logLevel, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter, Object result) {

        StringBuffer buffer = this.createLogInfoBuffer(name, entity);

        buffer.append("接收参数: [");
        buffer.append(LogUtil.getParamMap(entity.getParamNames(), args, paramNamesFilter));
        buffer.append("],");
        buffer.append("返回结果: [");
        buffer.append(LogUtil.parseParam(result));
        buffer.append("]");

        this.print(logLevel, buffer.toString());
    }

    @Override
    public void format(String name, LogMethodEntity entity, Throwable throwable) {

        StringBuffer buffer = this.createLogInfoBuffer(name, entity);

        buffer.append("异常信息：[");
        buffer.append(throwable.getLocalizedMessage());
        buffer.append("]");

        this.print(buffer.toString(), throwable);
    }
}
