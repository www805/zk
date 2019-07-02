package com.avst.zk.web.vo;

import com.avst.zk.common.param.ControlInfoParam;
import com.avst.zk.common.vo.ControlInfoParamVO;

import java.util.List;

public class ControlVO {

    private List<ControlInfoParamVO> pagelist;
    private ControlInfoParam pageparam;

    public List<ControlInfoParamVO> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<ControlInfoParamVO> pagelist) {
        this.pagelist = pagelist;
    }

    public ControlInfoParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(ControlInfoParam pageparam) {
        this.pageparam = pageparam;
    }
}
