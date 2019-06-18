package com.avst.zk.feignclient.base;

import com.avst.zk.common.util.StatusCode;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.feignclient.ec.EquipmentControl;
import com.avst.zk.feignclient.mc.MeetingControl;
import com.avst.zk.feignclient.trm.TrmControl;
import org.springframework.stereotype.Component;

@Component
public class ClientResult implements EquipmentControl, MeetingControl, TrmControl {

    @Override
    public ControlInfoParamVO getControlInfo() {
        ControlInfoParamVO infoVO = new ControlInfoParamVO();
        infoVO.setStatus(StatusCode.ERROR);//未连接
        return infoVO;
    }


}
