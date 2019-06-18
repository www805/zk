package com.avst.zk.outside.interfacetoout.v1.service;

import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.common.param.ControlInfoParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlInfoService extends BaseAction {

    public void getControlInfo(RResult result){
        //读取定时器存入缓存的数据
        List<ControlInfoParam> list = ControlCache.getControlInfoList("list");

        result.setData(list);

        changeResultToSuccess(result);
    }



}
