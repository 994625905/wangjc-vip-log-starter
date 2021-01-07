package vip.wangjc.log.builder.callback;

import vip.wangjc.log.builder.callback.abstracts.LogCallbackBuilder;
import vip.wangjc.log.entity.LogMethodEntity;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 默认的回调
 * @author wangjc
 * @title: DefaultLogCallbackBuilder
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 19:46
 */
public class DefaultLogCallbackBuilder extends LogCallbackBuilder {

    @Override
    public void callback(Annotation annotation, LogMethodEntity entity, Map<String, String> paramMap, Object result) {

        // TODO
    }
}
