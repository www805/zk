package com.avst.zk.outside.interfacetoout.v1.service;

import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.cache.param.AppCacheParam;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.util.iputil.SystemIpUtil;
import com.avst.zk.common.util.properties.PropertiesListenerConfig;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

            String myIP = SystemIpUtil.getOneUseableIp();

            ControlInfoParamVO controlInfoParamVO = new ControlInfoParamVO();//server.port
            controlInfoParamVO.setServername("zk");
            controlInfoParamVO.setServertitle("总控系统");
            controlInfoParamVO.setStatus(1);
            controlInfoParamVO.setCreatetime(DateUtil.getDateAndMinute());
            AppCacheParam cacheParam = AppCache.getAppCacheParam();
            String port= PropertiesListenerConfig.getProperty("server.port");
            String baseurl = ":" + port + "/main/gotomain";//默认的
            if(null!=cacheParam){//调用配置文件的地址进行组合
                try {
                    baseurl= ":" + port + ((Map<String,Object>)cacheParam.getData().get("guidepage")).get("url").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            controlInfoParamVO.setUrl(myIP +baseurl);

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
