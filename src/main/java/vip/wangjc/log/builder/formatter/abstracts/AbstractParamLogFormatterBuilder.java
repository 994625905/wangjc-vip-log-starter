package vip.wangjc.log.builder.formatter.abstracts;

import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * 参数日志格式化的构建器
 * @author wangjc
 * @title: ParamLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 19:58
 */
public abstract class AbstractParamLogFormatterBuilder extends BaseAbstractLogFormatter {

    /**
     * 格式化
     * @param level 日志级别
     * @param name 业务名称
     * @param entity 方法信息
     * @param args 参数列表
     * @param paramNamesFilter 参数过滤列表
     */
    public abstract void format(LogLevel level, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter);
}
