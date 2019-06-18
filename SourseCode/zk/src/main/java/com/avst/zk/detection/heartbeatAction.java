package com.avst.zk.detection;

import com.avst.zk.common.util.StatusCode;
import com.avst.zk.common.vo.ControlInfoParamVO;
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

    private ControlInfoParam eqInfoVO = new ControlInfoParamVO();
    private ControlInfoParam meInfoVO = new ControlInfoParamVO();
    private ControlInfoParam trmInfoVO = new ControlInfoParamVO();

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

        //请求会议是否正常
        ControlInfoParam eqInfo = equipmentControl.getControlInfo();
        eqInfoVO = getStatus(eqInfoVO, eqInfo);

        //请求设备是否正常
        ControlInfoParam meInfo = meetingControl.getControlInfo();
        meInfoVO = getStatus(meInfoVO, meInfo);

        //请求业务是否正常
        ControlInfoParam trmInfo = trmControl.getControlInfo();
        trmInfoVO = getStatus(trmInfoVO, trmInfo);

        //把已经注册服务信息的放到集合中
        List<ControlInfoParam> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(eqInfoVO.getName())){
            list.add(eqInfoVO);
        }
        if(StringUtils.isNotEmpty(meInfoVO.getName())){
            list.add(meInfoVO);
        }
        if(StringUtils.isNotEmpty(trmInfoVO.getName())){
            list.add(trmInfoVO);
        }

        System.out.println(list);

        //放到缓存里
        ControlCache.setControlInfoList("list", list);
    }

    /**
     * 获取连接状态
     * @param controlInfoVO
     * @param controlInfo
     * @return
     */
    public ControlInfoParam getStatus(ControlInfoParam controlInfoVO, ControlInfoParam controlInfo){
        //如果名字不为空，状态是0说明是中途断线了
        if(StringUtils.isNotEmpty(controlInfoVO.getName()) && controlInfo.getStatus() == 0){
            //如果是中途断开的就返回OFF
            controlInfoVO.setStatus(StatusCode.OFF);
        }else{
            //正常连接OK
            controlInfoVO = controlInfo;
        }
        return controlInfoVO;
    }

}
