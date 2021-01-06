package vip.wangjc.log.builder.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.log.builder.formatter.abstracts.AbstractResultLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;
import vip.wangjc.log.util.LogUtil;

/**
 * @author wangjc
 * @title: DefaultResultLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 20:05
 */
public class DefaultResultLogFormatterBuilder extends AbstractResultLogFormatterBuilder {

    private final static Logger logger = LoggerFactory.getLogger(DefaultResultLogFormatterBuilder.class);

    @Override
    public void format(LogLevel level, String name, LogMethodEntity entity, Object result) {

        StringBuffer buffer = this.createInfoBuilder(name, entity);

        buffer.insert(0,"异常日志——");
        buffer.append("返回结果: [");
        buffer.append(LogUtil.parseParam(result));
        buffer.append(']');

        this.print(logger, level, buffer.toString());
    }
}
