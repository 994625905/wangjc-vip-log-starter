package vip.wangjc.log.builder.formatter;

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

    @Override
    public void format(LogLevel level, String name, LogMethodEntity entity, Object result) {

        StringBuffer buffer = this.createResultLogInfoBuffer(name, entity);

        buffer.append("返回结果: [");
        buffer.append(LogUtil.parseParam(result));
        buffer.append(']');

        this.print(level, buffer.toString());
    }
}
