package com.avst.zk.action;

import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.service.ControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/control")
public class ControlAction extends BaseAction {

    @Autowired
    private ControlService controlService;

//    @RequestMapping(value = "/getControlList")
//    public RResult getTemplates(){
//        RResult result=this.createNewResultOfFail();
//        controlService.getControlList(result);
//        result.setEndtime(DateUtil.getDateAndMinute());
//        return result;
//    }

//4 7 10 1


}
