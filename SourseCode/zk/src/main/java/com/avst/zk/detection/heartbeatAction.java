package com.avst.zk.detection;

import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.StatusCode;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.common.vo.ToOutVO;
import com.avst.zk.feignclient.ec.EquipmentControl;
import com.avst.zk.feignclient.mc.MeetingControl;
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.common.param.ControlInfoParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时心跳检测各组件是否正常工作
 */
@Component
@EnableScheduling //定时任务
public class heartbeatAction {

    @Autowired
    private TrmControl trmControl; //业务服务器

    @Autowired
    private EquipmentControl equipmentControl;  //设备服务器

    @Autowired
    private MeetingControl meetingControl;  //会议服务器

    private ControlInfoParamVO eqInfoVO;
    private ControlInfoParamVO meInfoVO;
    private ControlInfoParamVO trmInfoVO;

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());


        ReqParam<ToOutVO> reqParam = new ReqParam();

        //请求会议是否正常
        RResult eqResult = equipmentControl.checkClient(reqParam);
        ToOutVO to = (ToOutVO) eqResult.getData();
        eqInfoVO = getStatus(eqInfoVO, to,"eq");

        ReqParam<ToOutVO> reqParam2 = new ReqParam();
        //请求设备是否正常
        RResult mcResult = meetingControl.checkClient(reqParam2);
        to = (ToOutVO) mcResult.getData();
        meInfoVO = getStatus(meInfoVO, to,"mc");

        ReqParam<ToOutVO> reqParam3 = new ReqParam();
        //请求业务是否正常
        RResult trmResult = trmControl.checkClient(reqParam3);
        to = (ToOutVO) trmResult.getData();
        trmInfoVO = getStatus(trmInfoVO, to,"eq");

        //把已经注册服务信息的放到集合中
//        List<ControlInfoParamVO> list = new ArrayList<>();
        List<ControlInfoParamVO> list = new ArrayList<>();

        list.add(eqInfoVO);
        list.add(meInfoVO);
        list.add(trmInfoVO);

        System.out.println(list);

        //放到缓存里
        ControlCache.setControlInfoList("list", list);
    }

    /**
     * 获取连接状态
     */
    public ControlInfoParamVO getStatus(ControlInfoParamVO controlInfoVO, ToOutVO to, String serverName) {

        if (null == controlInfoVO) {
            controlInfoVO = new ControlInfoParamVO();
            controlInfoVO.setCreatetime(DateUtil.getDateAndMinute());//设置当前时间
            controlInfoVO.setStatus(StatusCode.ERROR);
        }

        controlInfoVO.setTotal_item(to.getTotal_item());//总业务数
        controlInfoVO.setUse_item(to.getUse_item());//可使用业务数
        controlInfoVO.setServerName(serverName);//服务器名称

        //如果名字不为空，状态是0说明是中途断线了
        if (to.getTotal_item() == 0 && to.getUse_item() == 0) {
            //如果是中途断开的就返回OFF
            controlInfoVO.setStatus(StatusCode.ERROR);
        } else {
            //正常连接OK
            controlInfoVO.setStatus(StatusCode.OK);
        }
        return controlInfoVO;
    }

}
