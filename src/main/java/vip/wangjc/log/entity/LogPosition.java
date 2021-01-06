package vip.wangjc.log.entity;

/**
 * 日志定位
 * @author wangjc
 * @title: LogPostition
 * @projectName wangjc-vip
 * @date 2021/1/4 - 14:54
 */
public enum LogPosition {

    /**
     * 关闭（）
     */
    OFF(1),
    /**
     * 开启（优先级高于关闭）
     */
    ON(2);

    private LogPosition(int sort){
        this.sort = sort;
    }

    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
