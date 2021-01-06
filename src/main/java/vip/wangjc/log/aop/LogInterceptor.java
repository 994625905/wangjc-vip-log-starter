package vip.wangjc.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import vip.wangjc.log.annotation.Log;
import vip.wangjc.log.template.LogTemplate;

/**
 * 全局日志的切面拦截器
 * @author wangjc
 * @title: LogInterceptor
 * @projectName wangjc-vip
 * @date 2021/1/5 - 16:59
 */
public class LogInterceptor implements MethodInterceptor {

    private final LogTemplate logTemplate;

    public LogInterceptor(LogTemplate logTemplate){
        this.logTemplate = logTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Log log = methodInvocation.getMethod().getAnnotation(Log.class);
        Object result = null;
        try {
            result = methodInvocation.proceed();
            return result;
        } catch (Throwable throwable) {
            this.logTemplate.logPrint(methodInvocation, throwable, log);
            throw throwable;
        }finally {
            this.logTemplate.logPrint(methodInvocation, result, log);
        }
    }
}
