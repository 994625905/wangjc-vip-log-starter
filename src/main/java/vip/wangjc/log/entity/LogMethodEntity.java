package vip.wangjc.log.entity;

import java.util.List;

/**
 * 日志方法的实体
 * @author wangjc
 * @title: LogMethodEntity
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 14:38
 */
public class LogMethodEntity {

    /**
     * 本地行号：代表本地方法，不进行代码定位
     */
    public static final int NATIVE_LINE_NUMBER = 0;

    /**
     * 所在类的全类名
     */
    private String classAllName;

    /**
     * 所在类的简单类名
     */
    private String classSimpleName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数名称列表
     */
    private List<String> paramNames;

    /**
     * 参数值列表
     */
    private List<Object> paramValues;

    /**
     * 方法行号
     */
    private int lineNumber;

    public LogMethodEntity(String classAllName, String classSimpleName, String methodName, List<String> paramNames, List<Object> paramValues, int lineNumber){
        this.classAllName = classAllName;
        this.classSimpleName = classSimpleName;
        this.methodName = methodName;
        this.paramNames = paramNames;
        this.paramValues = paramValues;
        this.lineNumber = lineNumber;
    }

    /**
     * 判断是否为本地方法
     * @return
     */
    public boolean isNative(){
        return this.lineNumber == NATIVE_LINE_NUMBER;
    }

    public String getClassAllName() {
        return classAllName;
    }

    public void setClassAllName(String classAllName) {
        this.classAllName = classAllName;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public List<Object> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<Object> paramValues) {
        this.paramValues = paramValues;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
