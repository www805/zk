package com.avst.zk.outside.interfacetoout.v1.service;

import com.avst.zk.common.param.ControlInfoParam;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.outside.interfacetoout.v1.req.HeartbeatParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ControlInfoService extends BaseAction {

    public void getControlInfo(RResult result){
        //读取定时器存入缓存的数据
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");

        result.setData(list);

        changeResultToSuccess(result);
    }

    /**
     * 获取服务器时间
     * @param result
     */
    public void getControlTime(RResult result){
        //获取当前时间
        String dateAndMinute = DateUtil.getDateAndMinute();

        result.setData(dateAndMinute);

        changeResultToSuccess(result);
    }


    //getHeartbeat
    public void getHeartbeat(RResult result, ReqParam<ControlInfoParamVO> param) {

        //对所有赋值
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");
        if (null != list && list.size() > 0) {
            for (ControlInfoParamVO paramVO : list) {
                paramVO.setStatus(0);
            }
        }

        //放进缓存里面
        ControlInfoParamVO paramVO = param.getParam();
        System.out.println(paramVO);

        ControlCache.setControlInfo("list", paramVO);

        changeResultToSuccess(result);
    }

}
