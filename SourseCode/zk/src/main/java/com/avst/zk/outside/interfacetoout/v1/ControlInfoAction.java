package com.avst.zk.outside.interfacetoout.v1;

import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;

import com.avst.zk.outside.interfacetoout.v1.service.ControlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zk")
public class ControlInfoAction extends BaseAction {

    @Autowired
    private ControlInfoService controlInfoService;

    /**
     * 提供给外部，服务器信息列表接口
     * @return
     */
    @RequestMapping("/getControlInfoAll")
    public RResult getControlInfoAll(){
        RResult result=this.createNewResultOfFail();
        controlInfoService.getControlInfo(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 向外提供服务器时间
     * @return
     */
    @RequestMapping("/getControlTime")
    public RResult getControlTime(){
        RResult result=this.createNewResultOfFail();
        controlInfoService.getControlTime(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
