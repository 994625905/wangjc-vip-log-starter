package vip.wangjc.log.auto.configure;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.wangjc.log.aop.*;
import vip.wangjc.log.auto.properties.LogParamProperties;
import vip.wangjc.log.auto.properties.LogProperties;
import vip.wangjc.log.auto.properties.LogResultProperties;
import vip.wangjc.log.auto.properties.LogThrowingProperties;
import vip.wangjc.log.template.LogTemplate;

/**
 * 日志的自动化配置
 * @author wangjc
 * @title: LogAutoConfiguration
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 10:43
 */
@Configuration
@ConditionalOnClass(Logger.class)
@EnableConfigurationProperties({LogProperties.class, LogParamProperties.class, LogResultProperties.class, LogThrowingProperties.class})
public class LogAutoConfiguration {

    private final LogProperties logProperties;

    private final LogParamProperties logParamProperties;

    private final LogResultProperties logResultProperties;

    private final LogThrowingProperties logThrowingProperties;

    public LogAutoConfiguration(LogProperties logProperties, LogParamProperties logParamProperties, LogResultProperties logResultProperties, LogThrowingProperties logThrowingProperties){
        this.logProperties = logProperties;
        this.logParamProperties = logParamProperties;
        this.logResultProperties = logResultProperties;
        this.logThrowingProperties = logThrowingProperties;
    }

    /**
     * 注册日志模板方法bean
     * @return
     */
    @Bean
    public LogTemplate logTemplate(){
        return new LogTemplate(this.logProperties, this.logParamProperties, this.logResultProperties, this.logThrowingProperties);
    }

    /**
     * 注册全局日志拦截器
     * @param logTemplate
     * @return
     */
    @Bean
    @ConditionalOnBean(LogTemplate.class)
    public LogInterceptor logInterceptor(LogTemplate logTemplate){
        return new LogInterceptor(logTemplate);
    }

    /**
     * 注册全局日志aop通知
     * @param logInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(LogInterceptor.class)
    public LogAnnotationAdvisor logAnnotationAdvisor(LogInterceptor logInterceptor){
        return new LogAnnotationAdvisor(logInterceptor,100);
    }

    /**
     * 注册参数日志拦截器
     * @param logTemplate
     * @return
     */
    @Bean
    @ConditionalOnBean(LogTemplate.class)
    public LogParamInterceptor logParamInterceptor(LogTemplate logTemplate){
        return new LogParamInterceptor(logTemplate);
    }

    /**
     * 注册参数日志aop通知
     * @param logParamInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(LogParamInterceptor.class)
    public LogParamAnnotationAdvisor logParamAnnotationAdvisor(LogParamInterceptor logParamInterceptor){
        return new LogParamAnnotationAdvisor(logParamInterceptor, 101);
    }

    /**
     * 注册结果日志拦截器
     * @param logTemplate
     * @return
     */
    @Bean
    @ConditionalOnBean(LogTemplate.class)
    public LogResultInterceptor logResultInterceptor(LogTemplate logTemplate){
        return new LogResultInterceptor(logTemplate);
    }

    /**
     * 注册结果日志aop通知
     * @param logResultInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(LogResultInterceptor.class)
    public LogResultAnnotationAdvisor logResultAnnotationAdvisor(LogResultInterceptor logResultInterceptor){
        return new LogResultAnnotationAdvisor(logResultInterceptor, 102);
    }

    /**
     * 注册异常日志拦截器
     * @param logTemplate
     * @return
     */
    @Bean
    @ConditionalOnBean(LogTemplate.class)
    public LogThrowingInterceptor logThrowingInterceptor(LogTemplate logTemplate){
        return new LogThrowingInterceptor(logTemplate);
    }

    /**
     * 注册异常日志aop通知
     * @param logThrowingInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(LogThrowingInterceptor.class)
    public LogThrowingAnnotationAdvisor logThrowingAnnotationAdvisor(LogThrowingInterceptor logThrowingInterceptor){
        return new LogThrowingAnnotationAdvisor(logThrowingInterceptor, 103);
    }
}
