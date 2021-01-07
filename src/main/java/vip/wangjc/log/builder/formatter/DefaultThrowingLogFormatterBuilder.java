package vip.wangjc.log.builder.formatter;

import vip.wangjc.log.builder.formatter.abstracts.AbstractThrowingLogFormatterBuilder;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * @author wangjc
 * @title: DefaultThrowingLogFormatterBuilder
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/5 - 19:35
 */
public class DefaultThrowingLogFormatterBuilder extends AbstractThrowingLogFormatterBuilder {

    @Override
    public void format(String name, LogMethodEntity entity, Throwable throwable) {

        StringBuffer buffer = this.createThrowingLogInfoBuffer(name, entity);

        buffer.append("异常信息：[");
        buffer.append(throwable.getLocalizedMessage());
        buffer.append("]");

        this.print(buffer.toString(), throwable);
    }
}
