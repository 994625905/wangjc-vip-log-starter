package vip.wangjc.log.builder.formatter;

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

    @Override
    public void format(LogLevel level, String name, LogMethodEntity entity, Object[] args, String[] paramNamesFilter) {

        StringBuffer buffer = this.createParamLogInfoBuffer(name, entity);

        buffer.append("接收参数: [");
        buffer.append(LogUtil.getParamMap(entity.getParamNames(), args, paramNamesFilter));
        buffer.append("]");

        this.print(level, buffer.toString());
    }
}
