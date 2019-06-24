package com.avst.zk.outside.interfacetoout.v1.service;

import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
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

}
