package vip.wangjc.log.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类，参数解析，格式化……
 * @author wangjc
 * @title: LogUtil
 * @projectName wangjc-vip
 * @date 2021/1/5 - 17:42
 */
public class LogUtil {



    /**
     * 获取参数字典
     * @param paramNames 参数名称列表
     * @param paramValues 参数值数组
     * @return 返回参数字典
     */
    public static Map<String, String> getParamMap(List<String> paramNames, Object[] paramValues) {
        return getParamMap(paramNames, paramValues, null);
    }

    /**
     * 将参数拼成key-value形式
     * @param paramNames 参数名称列表
     * @param paramValues 参数值数组
     * @param filterParamNames 过滤参数列表
     * @return 返回参数字典
     */
    public static Map<String, String> getParamMap(List<String> paramNames, Object[] paramValues, String[] filterParamNames) {
        int count = paramNames.size();
        Map<String, String> paramMap = new LinkedHashMap<>(count);
        if (count == 0) {
            return paramMap;
        }

        if (filterParamNames!=null && filterParamNames.length>0) {
            for (int i = 0; i < count; i++) {
                if (!isParamFilter(filterParamNames, paramNames.get(i))) {
                    paramMap.put(paramNames.get(i), parseParam(paramValues[i]));
                }
            }
        }else {
            for (int i = 0; i < count; i++) {
                paramMap.put(paramNames.get(i), parseParam(paramValues[i]));
            }
        }
        return paramMap;
    }


    /**
     * 解析参数
     * @param param 参数
     * @return 返回参数字符串
     */
    public static String parseParam(Object param) {
        if (param==null) {
            return null;
        }
        Class<?> paramClass = param.getClass();
        if (Map.class.isAssignableFrom(paramClass)) {
            return parseMap(param);
        }
        if (Iterable.class.isAssignableFrom(paramClass)) {
            return parseIterable(param);
        }
        return paramClass.isArray() ? parseArray(param): param.toString();
    }

    /**
     * 参数过滤
     * @param filterParamNames：过滤的参数列表名称
     * @param paramName：
     * @return
     */
    private static Boolean isParamFilter(String[] filterParamNames, String paramName){
        boolean res = false;
        for(String param:filterParamNames){
            if(param.equals(paramName)){
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 解析字典
     * @param param 参数值
     * @return 返回参数字典字符串
     */
    private static String parseMap(Object param) {
        Map paramMap = (Map) param;
        Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
        if (!iterator.hasNext()) {
            return "{}";
        }
        StringBuffer builder = new StringBuffer();
        builder.append('{');
        Map.Entry entry;
        while (true) {
            entry = iterator.next();
            builder.append(entry.getKey()).append('=').append(parseParam(entry.getValue()));
            if (iterator.hasNext()) {
                builder.append(',').append(' ');
            }else {
                return builder.append('}').toString();
            }
        }
    }

    /**
     * 解析集合
     * @param param 参数值
     * @return 返回参数列表字符串
     */
    private static String parseIterable(Object param) {
        Iterator iterator = ((Iterable) param).iterator();
        if (!iterator.hasNext()) {
            return "[]";
        }
        StringBuffer builder = new StringBuffer();
        builder.append('[');
        while (true) {
            builder.append(parseParam(iterator.next()));
            if (iterator.hasNext()) {
                builder.append(',').append(' ');
            }else {
                return builder.append(']').toString();
            }
        }
    }

    /**
     * 解析数组
     * @param param 参数值
     * @return 返回参数列表字符串
     */
    private static String parseArray(Object param) {
        int length = Array.getLength(param);
        if (length==0) {
            return "[]";
        }
        StringBuffer builder = new StringBuffer();
        builder.append('[');
        for (int i = 0; i < length; i++) {
            builder.append(parseParam(Array.get(param, i)));
            if (i+1<length){
                builder.append(',').append(' ');
            }
        }
        return builder.append(']').toString();
    }



}
