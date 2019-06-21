package com.avst.zk.feignclient.base;

import com.avst.zk.common.util.StatusCode;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.common.vo.ToOutVO;
import com.avst.zk.feignclient.ec.EquipmentControl;
import com.avst.zk.feignclient.mc.MeetingControl;
import com.avst.zk.feignclient.trm.TrmControl;
import org.springframework.stereotype.Component;

@Component
public class ClientResult extends BaseAction implements EquipmentControl, MeetingControl, TrmControl {

    @Override
    public RResult<ToOutVO> checkClient(ReqParam param) {
        RResult rresult=createNewResultOfFail();
        ToOutVO toOutVO=new ToOutVO();
        toOutVO.setTotal_item(0);
        toOutVO.setUse_item(0);
        rresult.setData(toOutVO);
        changeResultToSuccess(rresult);
        return  rresult;
    }
}
