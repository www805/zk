package com.avst.zk.web.action;

import com.avst.zk.common.param.ControlInfoParam;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.web.service.ControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class ControlAction extends BaseAction {

    @Autowired
    private ControlService controlService;

    /**
     * 清空断开的服务器缓存
     */
    @RequestMapping("/clearControlInfo")
    public RResult clearControlInfo(){
        RResult result=this.createNewResultOfFail();
        controlService.clearControlInfo(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 提供给外部，服务器信息列表接口
     * @return
     */
    @RequestMapping("/getControlInfoAll")
    public RResult getControlInfoAll(@RequestBody ReqParam<ControlInfoParam> param){
        RResult result=this.createNewResultOfFail();
        controlService.getControlInfo(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/aaaa")
    public RResult getaaaa(){
        RResult result=this.createNewResultOfFail();


        for (int i = 0; i < 15; i++) {

            ControlInfoParamVO vo = new ControlInfoParamVO();

            vo.setServername("s" + i);
            vo.setStatus(1);
            System.out.println(vo.getServername());

            ControlCache.setControlInfo("list", vo);

        }

        changeResultToSuccess(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/bbbb")
    public RResult getbbbb(){
        RResult result=this.createNewResultOfFail();

        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");

        int numSize = list.size() - 3;
        ControlInfoParamVO vo = null;

        for (int i = 0; i < numSize; i++) {

            vo = list.get(i);

            vo.setStatus(0);

        }

        changeResultToSuccess(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


}
