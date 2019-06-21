package com.avst.zk.common.vo;

import com.avst.zk.common.util.StatusCode;

import java.util.Date;

public class ControlInfoParamVO {

    //服务名称
    private String ServerName;

    private Integer total_item;//总业务数

    private Integer use_item;//可使用业务数

    //服务状态
    private Integer status;

    //创建时间
    private String createtime;

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "ControlInfoParamVO{" +
                "ServerName='" + ServerName + '\'' +
                ", total_item=" + total_item +
                ", use_item=" + use_item +
                ", status=" + status +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
