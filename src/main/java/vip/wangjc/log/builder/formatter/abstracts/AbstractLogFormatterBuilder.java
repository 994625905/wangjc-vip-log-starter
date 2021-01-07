package vip.wangjc.log.builder.formatter.abstracts;

import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * 日志格式化的构建器
 * @author wangjc
 * @title: LogFilterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 15:25
 */
public abstract class AbstractLogFormatterBuilder extends BaseAbstractLogFormatter{

    /**
     * 日志格式化
     * @param logLevel：日志级别
     * @param name：业务名
     * @param entity：日志方法信息
     * @param args：参数列表
     * @param paramNamesFilter：参数过滤列表
     * @param result：返回结果
     */
    public abstract void format(LogLevel logLevel, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter, Object result);

    /**
     * 格式化
     * @param name：业务名称
     * @param entity：方法信息
     * @param throwable：异常
     */
    public abstract void format(String name, LogMethodEntity entity, Throwable throwable);

    /**
     * 创建日志信息的buffer
     * @param name
     * @param entity
     * @return
     */
    public StringBuffer createLogInfoBuffer(String name, LogMethodEntity entity){
        StringBuffer buffer = this.createInfoBuffer(name, entity);

        buffer.insert(0,"综合日志");
        return buffer;
    }
}
