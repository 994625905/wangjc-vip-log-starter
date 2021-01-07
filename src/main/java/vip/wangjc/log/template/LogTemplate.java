package vip.wangjc.log.template;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.log.annotation.Log;
import vip.wangjc.log.annotation.LogParam;
import vip.wangjc.log.annotation.LogResult;
import vip.wangjc.log.annotation.LogThrowing;
import vip.wangjc.log.auto.properties.LogParamProperties;
import vip.wangjc.log.auto.properties.LogProperties;
import vip.wangjc.log.auto.properties.LogResultProperties;
import vip.wangjc.log.auto.properties.LogThrowingProperties;
import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractParamLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractResultLogFormatterBuilder;
import vip.wangjc.log.builder.formatter.abstracts.AbstractThrowingLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;
import vip.wangjc.log.entity.LogPosition;
import vip.wangjc.log.util.LogUtil;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjc
 * @title: LogTemplate
 * @projectName wangjc-vip
 * @date 2021/1/5 - 10:49
 */
public class LogTemplate {

    private static final Logger logger = LoggerFactory.getLogger(LogTemplate.class);

    private final LogProperties logProperties;

    private final LogParamProperties logParamProperties;

    private final LogResultProperties logResultProperties;

    private final LogThrowingProperties logThrowingProperties;

    public LogTemplate(LogProperties logProperties, LogParamProperties logParamProperties, LogResultProperties logResultProperties, LogThrowingProperties logThrowingProperties){
        this.logProperties = logProperties;
        this.logParamProperties = logParamProperties;
        this.logResultProperties = logResultProperties;
        this.logThrowingProperties = logThrowingProperties;
    }

    /**
     * 类池
     */
    private ClassPool CLASS_POOL;

    @PostConstruct
    public void init(){
        this.CLASS_POOL = new ClassPool(true);
        this.CLASS_POOL.insertClassPath(new ClassClassPath(LogTemplate.class));
    }

    /**
     * 打印参数日志
     * @param methodInvocation
     * @param logParam
     */
    public void logParamPrint(MethodInvocation methodInvocation, LogParam logParam){
        try {
            /** 日志级别比较--配置优先 */
            LogLevel level = this.compareLoglevel(logParam.level(), this.logParamProperties.getLevel());
            if(level == null){
                return;
            }
            /** 是否打印 */
            if(!this.isEnable(level)){
                return;
            }

            /** 代码位置比较--配置优先，获取方法信息实体 */
            LogPosition position = this.compareLogPosition(logParam.position(), this.logParamProperties.getPosition());
            LogMethodEntity logMethodEntity = this.getLogMethodEntity(methodInvocation, position);

            /** 参数日志格式化构建器--配置优先 */
            AbstractParamLogFormatterBuilder formatterBuilder = logParam.formatter().newInstance();
            if(!this.logParamProperties.getFormatter().getName().equals(logParam.formatter().getName())){
                formatterBuilder = this.logParamProperties.getFormatter().newInstance();
            }
            formatterBuilder.format(level, logParam.name(), logMethodEntity, methodInvocation.getArguments(), logParam.paramFilter());

            /** 回调 */
            this.callback(logParam.callback(), logParam, logMethodEntity, methodInvocation, null);
        } catch (Exception e) {
            logger.error("print LogParam error!reason[{}]",e.getMessage());
        }
    }

    /**
     * 打印结果日志
     * @param methodInvocation
     * @param result
     * @param logResult
     */
    public void logResultPrint(MethodInvocation methodInvocation, Object result, LogResult logResult){
        try {
            /** 日志级别比较--优先配置 */
            LogLevel level = this.compareLoglevel(logResult.level(), this.logResultProperties.getLevel());
            if(level == null){
                return;
            }
            /** 是否打印 */
            if(!this.isEnable(level)){
                return;
            }

            /** 代码位置比较--优先配置，获取方法信息实体 */
            LogPosition position = this.compareLogPosition(logResult.position(), this.logResultProperties.getPosition());
            LogMethodEntity logMethodEntity = this.getLogMethodEntity(methodInvocation, position);

            /** 结果日志格式化构建器--配置优先 */
            AbstractResultLogFormatterBuilder formatterBuilder = logResult.formatter().newInstance();
            if(!this.logResultProperties.getFormatter().getName().equals(logResult.formatter().getName())){
                formatterBuilder = this.logResultProperties.getFormatter().newInstance();
            }
            formatterBuilder.format(level, logResult.name(), logMethodEntity, result);

            /** 回调 */
            this.callback(logResult.callback(), logResult, logMethodEntity, methodInvocation, result);
        } catch (Exception e) {
            logger.error("print LogResult error!reason[{}]",e.getMessage());
        }
    }

    /**
     * 打印异常日志
     * @param methodInvocation
     * @param throwable
     * @param logThrowing
     */
    public void logThrowingPrint(MethodInvocation methodInvocation, Throwable throwable, LogThrowing logThrowing){
        try {
            /** 获取方法信息实体 */
            LogMethodEntity logMethodEntity = this.getLogMethodEntity(methodInvocation, null);

            /** 异常日志格式化构建器--配置优先 */
            AbstractThrowingLogFormatterBuilder formatterBuilder = logThrowing.formatter().newInstance();
            if(!this.logThrowingProperties.getFormatter().getName().equals(logThrowing.formatter().getName())){
                formatterBuilder = this.logThrowingProperties.getFormatter().newInstance();
            }
            formatterBuilder.format(logThrowing.name(), logMethodEntity, throwable);

            /** 执行回调 */
            this.callback(logThrowing.callback(), logThrowing, logMethodEntity, methodInvocation,null);
        } catch (Exception e) {
            logger.error("print LogThrowing error!reason[{}]",e.getMessage());
        }
    }

    /**
     * 打印日志：异常
     * @param methodInvocation
     * @param throwable
     * @param log
     */
    public void logPrint(MethodInvocation methodInvocation, Throwable throwable, Log log){
        try {
            /** 获取方法信息实体 */
            LogMethodEntity logMethodEntity = this.getLogMethodEntity(methodInvocation, null);

            /** 日志格式化构建器--配置优先 */
            AbstractLogFormatterBuilder formatterBuilder = log.formatter().newInstance();
            if(!this.logProperties.getFormatter().getName().equals(log.formatter().getName())){
                formatterBuilder = this.logProperties.getFormatter().newInstance();
            }
            formatterBuilder.format(log.name(), logMethodEntity, throwable);

            /** 执行回调 */
            this.callback(log.callback(), log, logMethodEntity, methodInvocation,null);
        } catch (Exception e) {
            logger.error("print Log error!reason[{}]",e.getMessage());
        }
    }

    /**
     * 打印日志：环绕
     * @param methodInvocation
     * @param log
     */
    public void logPrint(MethodInvocation methodInvocation, Object result, Log log){
        try {
            /** 日志级别比较--配置优先 */
            LogLevel level = this.compareLoglevel(log.level(), this.logProperties.getLevel());
            if(level == null){
                return;
            }
            /** 是否打印 */
            if(!this.isEnable(level)){
                return;
            }

            /** 代码位置比较--配置优先，获取方法信息实体 */
            LogPosition position = this.compareLogPosition(log.position(), this.logProperties.getPosition());
            LogMethodEntity logMethodEntity = this.getLogMethodEntity(methodInvocation, position);

            /** 日志格式化构建器--配置优先 */
            AbstractLogFormatterBuilder formatterBuilder = log.formatter().newInstance();
            if(!this.logProperties.getFormatter().getName().equals(log.formatter().getName())){
                formatterBuilder = this.logProperties.getFormatter().newInstance();
            }
            formatterBuilder.format(level, log.name(), logMethodEntity, methodInvocation.getArguments(), log.paramsFilter(), result);

            /** 执行回调 */
            this.callback(log.callback(), log, logMethodEntity, methodInvocation, result);
        } catch (Exception e) {
            logger.error("print Log error!reason[{}]",e.getMessage());
        }
    }

    /**
     * 日志回调
     * @param callback
     * @param logAnnotation
     * @param logMethodEntity
     * @param methodInvocation
     * @param result
     */
    private void callback(Class<? extends LogCallbackBuilder> callback, Annotation logAnnotation, LogMethodEntity logMethodEntity, MethodInvocation methodInvocation, Object result) {
        try {
            /** 回调构建器--配置优先 */
            LogCallbackBuilder callbackBuilder = callback.newInstance();
            if(!this.logProperties.getCallback().getName().equals(callback.getName())){
                callbackBuilder = this.logProperties.getCallback().newInstance();
            }
            callbackBuilder.callback(logAnnotation, logMethodEntity, LogUtil.getParamMap(logMethodEntity.getParamNames(), methodInvocation.getArguments()), result);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("{}.{}方法日志回调错误：【{}】", logMethodEntity.getClassAllName(), logMethodEntity.getMethodName(), e.getMessage());
        }
    }

    /**
     * 是否开启打印日志，以logger为参照
     * @param logLevel
     * @return
     */
    private Boolean isEnable(LogLevel logLevel){
        switch (logLevel){
            case debug:
                return logger.isDebugEnabled();
            case info:
                return logger.isDebugEnabled() || logger.isInfoEnabled();
            case warn:
                return logger.isDebugEnabled() || logger.isInfoEnabled() || logger.isWarnEnabled();
            case error:
                return logger.isDebugEnabled() || logger.isInfoEnabled() || logger.isWarnEnabled() || logger.isErrorEnabled();
            default:
                return false;
        }
    }

    /**
     * 比较日志设置的优先级
     * @param logLevel
     * @param compareLoglevel
     * @return
     */
    private LogLevel compareLoglevel(LogLevel logLevel, LogLevel compareLoglevel){
        if(logLevel.getSort() >= compareLoglevel.getSort()){
            return logLevel;
        }
        return null;
    }

    /**
     * 比较日志位置的优先级
     * @param position
     * @param comparePosition
     * @return
     */
    private LogPosition compareLogPosition(LogPosition position, LogPosition comparePosition){
        if(position.getSort() <= comparePosition.getSort()){
            return position;
        }
        return null;
    }

    /**
     * 获取方法信息实体
     * @param methodInvocation
     * @param position
     * @return
     */
    private LogMethodEntity getLogMethodEntity(MethodInvocation methodInvocation, LogPosition position){
        if(position == null){
            return this.getMethodInfo(methodInvocation, LogMethodEntity.NATIVE_LINE_NUMBER);
        }
        return this.getMethodInfo(methodInvocation);
    }

    /**
     * 获取方法信息
     * @param methodInvocation
     * @return
     */
    private LogMethodEntity getMethodInfo(MethodInvocation methodInvocation) {
        try {
            Method method = methodInvocation.getMethod();
            CtMethod ctMethod = this.CLASS_POOL.get(method.getDeclaringClass().getName()).getDeclaredMethod(method.getName());
            int lineNumber = ctMethod.getMethodInfo().getLineNumber(0);
            return getMethodInfo(methodInvocation, lineNumber);
        } catch (NotFoundException e) {
            return getMethodInfo(methodInvocation, LogMethodEntity.NATIVE_LINE_NUMBER);
        }
    }

    /**
     * 获取方法信息
     * @param methodInvocation
     * @param lineNumber
     * @return
     */
    private LogMethodEntity getMethodInfo(MethodInvocation methodInvocation, int lineNumber){
        Class<?> declaringClass = methodInvocation.getMethod().getDeclaringClass();
        Parameter[] parameters = methodInvocation.getMethod().getParameters();
        Object[] arguments = methodInvocation.getArguments();

        List<String> paramNames = new ArrayList<>(parameters.length);
        List<Object> paramValues = new ArrayList<>(arguments.length);

        for(Parameter param:parameters){
            paramNames.add(param.getName());
        }
        for(Object arg:arguments){
            paramValues.add(arg);
        }
        return new LogMethodEntity(declaringClass.getName(), declaringClass.getSimpleName(), methodInvocation.getMethod().getName(), paramNames, paramValues, lineNumber);
    }

}
