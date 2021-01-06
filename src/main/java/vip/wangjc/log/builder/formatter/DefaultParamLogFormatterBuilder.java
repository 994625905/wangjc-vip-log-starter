package vip.wangjc.log.builder.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.log.builder.formatter.abstracts.AbstractParamLogFormatterBuilder;
import vip.wangjc.log.entity.LogLevel;
import vip.wangjc.log.entity.LogMethodEntity;
import vip.wangjc.log.util.LogUtil;

/**
 * @author wangjc
 * @title: DefaultParamLogFormatterBuilder
 * @projectName wangjc-vip
 * @date 2021/1/4 - 20:00
 */
public class DefaultParamLogFormatterBuilder extends AbstractParamLogFormatterBuilder {

    private final static Logger logger = LoggerFactory.getLogger(DefaultParamLogFormatterBuilder.class);

    @Override
    public void format(LogLevel level, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter) {

        StringBuffer buffer = this.createInfoBuilder(name, entity);

        buffer.insert(0,"参数日志——");
        buffer.append("接收参数: [");
        buffer.append(LogUtil.getParamMap(entity.getParamNames(), args, paramNamesFilter));
        buffer.append("]");

        this.print(logger, level, buffer.toString());
    }
}
