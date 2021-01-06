package vip.wangjc.log.builder.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.log.builder.formatter.abstracts.AbstractThrowingLogFormatterBuilder;
import vip.wangjc.log.entity.LogMethodEntity;

/**
 * @author wangjc
 * @title: DefaultThrowingLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/5 - 19:35
 */
public class DefaultThrowingLogFormatterBuilder extends AbstractThrowingLogFormatterBuilder {

    private final static Logger logger = LoggerFactory.getLogger(DefaultThrowingLogFormatterBuilder.class);

    @Override
    public void format(String name, LogMethodEntity entity, Throwable throwable) {

        StringBuffer buffer = createInfoBuilder(name, entity);

        buffer.insert(0,"异常日志——");
        buffer.append("异常信息：[");
        buffer.append(throwable.getLocalizedMessage());
        buffer.append("]");

        this.print(logger, buffer.toString(), throwable);
    }
}
