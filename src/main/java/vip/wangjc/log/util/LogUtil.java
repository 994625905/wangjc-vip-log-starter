package vip.wangjc.log.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类，参数解析，格式化……
 * @author wangjc
 * @title: LogUtil
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 17:42
 */
public class LogUtil {

    private static final String FIELD_SERIAL_UID = "serialVersionUID";

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
        /** Integer、Long、Short、Boolean、Byte、Character、Double、Float */
        if(param instanceof Integer || param instanceof Long || param instanceof Short || param instanceof Byte ||
                param instanceof Boolean || param instanceof Double || param instanceof Float || param instanceof Character || param instanceof String){
            return param.toString();
        }

        Class<?> paramClass = param.getClass();

        /** Map类型 */
        if (Map.class.isAssignableFrom(paramClass)) {
            return parseMap(param);
        }

        /** 集合类型 */
        if (Iterable.class.isAssignableFrom(paramClass)) {
            return parseIterable(param);
        }

        /** 数组类型 */
        if(paramClass.isArray()){
            return parseArray(param);
        }

        /** 实体类型 */
        return parseEntity(param);
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

    /**
     * 解析实体
     * @param param
     * @return
     */
    private static String parseEntity(Object param){
        try {
            StringBuffer buffer = new StringBuffer();
            Field[] fields = null;
            Class<?> paramClass = param.getClass();
            while (paramClass != null){
                Field[] declaredFields = paramClass.getDeclaredFields();
                if(fields == null){
                    fields = declaredFields;
                }else{
                    fields = append(fields,declaredFields);
                }
                paramClass = paramClass.getSuperclass();
            }

            buffer.append("{");
            for(int i=0; i<fields.length; i++){
                Field f = fields[i];

                /** 过滤掉序列号ID */
                if(FIELD_SERIAL_UID.equals(f.getName())){
                    continue;
                }
                f.setAccessible(true);//设置可见性
                Object value = f.get(param);

                /** 过滤掉空值字段 */
                if(value == null){
                    continue;
                }
                buffer.append(f.getName());
                buffer.append("=");
                buffer.append(parseParam(value));
                if(i< fields.length - 1){
                    buffer.append(",");
                }
            }
            buffer.append("}");
            return buffer.toString();
        } catch (Exception e) {
            return param.toString();
        }
    }

    /**
     * 追加字段
     * @param buffer
     * @param newElements
     * @return
     */
    private static Field[] append(Field[] buffer,Field... newElements){
        if(isEmpty(buffer)){
            return newElements;
        }else{
            return insert(buffer,buffer.length,newElements);
        }
    }
    /**
     * 字段添加
     * @param array
     * @param index
     * @param newFieldlements
     * @return
     */
    private static Field[] insert(Field[] array,int index,Field... newFieldlements){
        if(isEmpty(newFieldlements)){
            return array;
        }
        if(isEmpty(array)){
            return newFieldlements;
        }
        final int len = length(array);
        if(index < 0){
            index = (index % len) + len;
        }

        Field[] result = newArray(array.getClass().getComponentType(),Math.max(len,index)+newFieldlements.length);
        System.arraycopy(array,0,result,0,Math.min(len,index));
        System.arraycopy(newFieldlements,0,result,index,newFieldlements.length);
        if(index < len){
            System.arraycopy(array,index,result,index+newFieldlements.length,len - index);
        }
        return result;
    }

    /**
     * 获取新的字段数组
     * @param componentType
     * @param newSize
     * @return
     */
    private static Field[] newArray(Class<?> componentType,int newSize){
        return (Field[]) Array.newInstance(componentType,newSize);
    }

    /**
     * 判断类的字段非空
     * @param array
     * @return
     */
    private static boolean isEmpty(Field... array){
        return array == null || array.length == 0;
    }

    /**
     * 获取指定数组类长度，私有
     * @param array
     * @return
     */
    private static int length(Object array) throws IllegalArgumentException{
        if(null == array){
            return 0;
        }else {
            return Array.getLength(array);
        }
    }

}
