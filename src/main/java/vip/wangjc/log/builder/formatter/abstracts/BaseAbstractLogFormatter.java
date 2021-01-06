package vip.wangjc.log.builder.formatter.abstracts;

import org.slf4j.Logger;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * @author wangjc
 * @title: BaseAbstractLogFormatter
 * @projectName wangjc-vip
 * @date 2021/1/5 - 19:08
 */
public abstract class BaseAbstractLogFormatter {

    /**
     * 打印信息
     * @param logger 日志对象
     * @param level 日志级别
     * @param msg 输出信息
     */
    public void print(Logger logger, LogLevel level, String msg) {
        switch (level){
            case debug:
                logger.debug(msg);
                break;
            case info:
                logger.info(msg);
                break;
            case warn:
                logger.warn(msg);
                break;
            case error:
                logger.error(msg);
                break;
            default:
                logger.error("log level resolve fail!");
                break;
        }
    }

    /**
     * 打印异常
     * @param logger
     * @param msg
     * @param throwable
     */
    public void print(Logger logger, String msg, Throwable throwable){
        logger.error(msg,throwable);
    }

    /**
     * 创建日志信息的builder
     * @param name
     * @param entity
     * @return
     */
    public StringBuffer createInfoBuilder(String name, LogMethodEntity entity){
        StringBuffer buffer = new StringBuffer();
        buffer.append("调用方法: [");
        if (entity.isNative()) {
            buffer.append(entity.getClassAllName()).append('.').append(entity.getMethodName());
        }else {

            /** 构造方法栈 */
            StackTraceElement stackTraceElement = new StackTraceElement(entity.getClassAllName(), entity.getMethodName(), entity.getClassSimpleName() + ".java", entity.getLineNumber());
            buffer.append(stackTraceElement);
        }
        buffer.append("],");
        buffer.append("业务名称: [");
        buffer.append(name);
        buffer.append("],");
        return buffer;
    }

}
