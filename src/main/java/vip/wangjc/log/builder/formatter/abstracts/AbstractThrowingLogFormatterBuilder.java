package vip.wangjc.log.builder.formatter.abstracts;

import vip.wangjc.log.entity.LogMethodEntity;

/**
 * 异常日志格式化的构建器
 * @author wangjc
 * @title: AbstractThrowingLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/5 - 19:33
 */
public abstract class AbstractThrowingLogFormatterBuilder extends BaseAbstractLogFormatter{

    /**
     * 格式化
     * @param name：业务名称
     * @param entity：方法信息
     * @param throwable：异常
     */
    public abstract void format(String name, LogMethodEntity entity, Throwable throwable);

    /**
     * 创建异常日志信息的buffer
     * @param name
     * @param entity
     * @return
     */
    public StringBuffer createThrowingLogInfoBuffer(String name, LogMethodEntity entity){
        StringBuffer buffer = this.createInfoBuffer(name, entity);

        buffer.insert(0,"异常日志");
        return buffer;
    }
}
