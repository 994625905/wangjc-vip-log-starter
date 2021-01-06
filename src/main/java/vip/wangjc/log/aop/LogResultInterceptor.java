package vip.wangjc.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import vip.wangjc.log.annotation.LogResult;
import vip.wangjc.log.template.LogTemplate;

/**
 * 结果日志的切面拦截器
 * @author wangjc
 * @title: LogResultInterceptor
 * @projectName wangjc-vip
 * @date 2021/1/5 - 17:20
 */
public class LogResultInterceptor implements MethodInterceptor {

    private final LogTemplate logTemplate;

    public LogResultInterceptor(LogTemplate logTemplate){
        this.logTemplate = logTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogResult logResult = methodInvocation.getMethod().getAnnotation(LogResult.class);
        Object result = null;
        try {
            result = methodInvocation.proceed();
            return result;
        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            this.logTemplate.logResultPrint(methodInvocation, result, logResult);
        }
    }
}
