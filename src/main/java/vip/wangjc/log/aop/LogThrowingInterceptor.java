package vip.wangjc.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import vip.wangjc.log.annotation.LogThrowing;
import vip.wangjc.log.template.LogTemplate;

/**
 * 异常日志的切面拦截器
 * @author wangjc
 * @title: LogThrowingInterceptor
 * @projectName wangjc-vip
 * @date 2021/1/5 - 17:26
 */
public class LogThrowingInterceptor implements MethodInterceptor {

    private final LogTemplate logTemplate;

    public LogThrowingInterceptor(LogTemplate logTemplate){
        this.logTemplate = logTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogThrowing logThrowing = methodInvocation.getMethod().getAnnotation(LogThrowing.class);
        try {
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            this.logTemplate.logThrowingPrint(methodInvocation, throwable, logThrowing);
            throw throwable;
        }
    }
}
