package com.avst.zk.common.vo;

public class ToOutVO {
    private Integer total_item;//总业务数

    private Integer use_item;//可使用业务数

    public Integer getTotal_item() {
        return total_item;
    }

    public void setTotal_item(Integer total_item) {
        this.total_item = total_item;
    }

    public Integer getUse_item() {
        return use_item;
    }

    public void setUse_item(Integer use_item) {
        this.use_item = use_item;
    }

    @Override
    public String toString() {
        return "ToOutVO{" +
                "total_item=" + total_item +
                ", use_item=" + use_item +
                '}';
    }
}