package com.avst.zk.outside.interfacetoout.v1.service;

import com.avst.zk.common.param.ControlInfoParam;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.NetTool;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.outside.interfacetoout.v1.req.HeartbeatParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ControlInfoService extends BaseAction {

    public void getControlInfo(RResult result){
        //读取定时器存入缓存的数据
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");

        ArrayList<ControlInfoParamVO> arrayList = new ArrayList<>();
        if (null != list) {
            for (ControlInfoParamVO vo : list) {
                arrayList.add(vo);
            }

            String myIP = NetTool.getMyIP();

            ControlInfoParamVO controlInfoParamVO = new ControlInfoParamVO();
            controlInfoParamVO.setServername("zk");
            controlInfoParamVO.setServertitle("总控系统");
            controlInfoParamVO.setStatus(1);
            controlInfoParamVO.setCreatetime(DateUtil.getDateAndMinute());
            controlInfoParamVO.setUrl(myIP + ":8079/main/gotomain");

            arrayList.add(controlInfoParamVO);
        }


        result.setData(arrayList);

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

        //放进缓存里面
        ControlInfoParamVO paramVO = param.getParam();
//        System.out.println(paramVO);

        if(null != paramVO){
            ControlCache.setControlInfo("list", paramVO);

            LogUtil.intoLog(ControlCache.getControlInfoByServername("list", paramVO.getServername()));
            changeResultToSuccess(result);
        }

    }

}
