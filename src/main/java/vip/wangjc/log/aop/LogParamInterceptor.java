package vip.wangjc.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import vip.wangjc.log.annotation.LogParam;
import vip.wangjc.log.template.LogTemplate;

/**
 * 参数日志的切面拦截器
 * @author wangjc
 * @title: LogParamInterceptor
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 17:13
 */
public class LogParamInterceptor implements MethodInterceptor {

    private final LogTemplate logTemplate;

    public LogParamInterceptor(LogTemplate logTemplate){
        this.logTemplate = logTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogParam logParam = methodInvocation.getMethod().getAnnotation(LogParam.class);
        this.logTemplate.logParamPrint(methodInvocation,logParam);
        return methodInvocation.proceed();
    }
}
