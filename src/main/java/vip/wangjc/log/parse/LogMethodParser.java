package vip.wangjc.log.parse;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.aopalliance.intercept.MethodInvocation;
import vip.wangjc.log.entity.LogMethodEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjc
 * @title: LogMethodParser
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 14:11
 */
public class LogMethodParser {

    private static final ClassPool CLASS_POOL = initClassPool();

    /**
     * 初始化类池
     * @return 返回类池
     */
    private static ClassPool initClassPool(){
        ClassPool pool = new ClassPool(true);
        pool.insertClassPath(new ClassClassPath(LogMethodParser.class));
        return pool;
    }

    /**
     * 获取方法信息
     * @param methodInvocation
     * @param lineNumber
     * @return
     */
    public static LogMethodEntity getMethodInfo(MethodInvocation methodInvocation, int lineNumber){
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

    /**
     * 获取方法信息
     * @param methodInvocation
     * @return
     */
    public static LogMethodEntity getMethodInfo(MethodInvocation methodInvocation) {
        try {
            Method method = methodInvocation.getMethod();
            CtMethod ctMethod = CLASS_POOL.get(method.getDeclaringClass().getName()).getDeclaredMethod(method.getName());
            int lineNumber = ctMethod.getMethodInfo().getLineNumber(0);
            return getMethodInfo(methodInvocation, lineNumber);
        } catch (NotFoundException e) {
            return getMethodInfo(methodInvocation, LogMethodEntity.NATIVE_LINE_NUMBER);
        }
    }
}
