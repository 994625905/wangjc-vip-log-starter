package vip.wangjc.log.entity;

/**
 * 日志级别
 * @author wangjc
 * @title: LogLevel
 * @projectName wangjc-vip-log-starter
 * @date 2021/1/4 - 14:48
 */
public enum LogLevel {

    /** 优先级从低到高，依次排列 */

    /**
     * 调试，级别1
     */
    debug(1),
    /**
     * 信息，级别2
     */
    info(2),
    /**
     * 警告，级别3
     */
    warn(3),
    /**
     * 错误，级别4
     */
    error(4);

    private LogLevel(int sort){
        this.sort = sort;
    }

    /**
     * 优先级
     */
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
