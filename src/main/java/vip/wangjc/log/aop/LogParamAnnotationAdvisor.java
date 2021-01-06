package vip.wangjc.log.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import vip.wangjc.log.annotation.LogParam;

/**
 * 参数日志的AOP通知
 * @author wangjc
 * @title: LogParamAnnotationAdvisor
 * @projectName wangjc-vip
 * @date 2021/1/5 - 17:18
 */
public class LogParamAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private final Advice advice;

    private final Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(LogParam.class);

    public LogParamAnnotationAdvisor(LogParamInterceptor interceptor, int order){
        this.advice = interceptor;
        setOrder(order);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if(this.advice instanceof BeanFactoryAware){
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}
