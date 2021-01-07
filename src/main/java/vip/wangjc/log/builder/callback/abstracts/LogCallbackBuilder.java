package vip.wangjc.log.builder.callback.abstracts;

import vip.wangjc.log.entity.LogMethodEntity;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 日志回调的构建器
 * @author wangjc
 * @title: LogCallbackBuilder
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 19:43
 */
public abstract class LogCallbackBuilder {

    /**
     * 回调方法
     * @param annotation：当前使用注解
     * @param entity：方法信息
     * @param paramMap：参数字典
     * @param result：方法调用结果
     */
    public abstract void callback(Annotation annotation, LogMethodEntity entity, Map<String,String> paramMap, Object result);
}
