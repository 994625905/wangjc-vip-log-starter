package vip.wangjc.log.builder.formatter.abstracts;

import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * 结果日志格式化的构建器
 * @author wangjc
 * @title: ResultLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 20:03
 */
public abstract class AbstractResultLogFormatterBuilder extends BaseAbstractLogFormatter {

    /**
     * 格式化
     * @param level：日志级别
     * @param name：业务名称
     * @param entity：方法信息
     * @param result：返回结果
     */
    public abstract void format(LogLevel level, String name, LogMethodEntity entity, Object result);

    /**
     * 创建结果日志信息的buffer
     * @param name
     * @param entity
     * @return
     */
    public StringBuffer createResultLogInfoBuffer(String name, LogMethodEntity entity){
        StringBuffer buffer = this.createInfoBuffer(name, entity);

        buffer.insert(0,"结果日志");
        return buffer;
    }
}
