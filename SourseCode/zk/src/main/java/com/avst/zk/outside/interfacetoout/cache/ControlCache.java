package com.avst.zk.outside.interfacetoout.cache;



import com.avst.zk.common.vo.ControlInfoParamVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 总控缓存
 */
public class ControlCache {

    private static Map<String, List<ControlInfoParamVO>> fdMap;

    public static synchronized  List<ControlInfoParamVO> getControlInfoList(String ciid){
        if(null!=fdMap&&fdMap.containsKey(ciid)){
            return fdMap.get(ciid);
        }
        return null;
    }


    public static synchronized ControlInfoParamVO getControlInfoByFDIp(String ciid, String ip){
        List<ControlInfoParamVO> list=getControlInfoList(ciid);
        if(null!=list&&list.size() > 0){
            for(ControlInfoParamVO ci:list){
//                if(ip.equals(ci.getIp())){
//                    return ci;
//                }
            }
        }
        return null;
    }
    public static synchronized  boolean setControlInfoList(String ciid,List<ControlInfoParamVO> cilist){

        if(null==fdMap){
            fdMap=new HashMap<String, List<ControlInfoParamVO>>();
        }
        if(fdMap.containsKey(ciid)){
            fdMap.remove(ciid);
        }
        fdMap.put(ciid,cilist);
        return true;
    }

    public static synchronized  boolean setControlInfo(String ciid, ControlInfoParamVO ciparam){
        List<ControlInfoParamVO> list=getControlInfoList(ciid);
        if(null!=list){
            if(list.size() > 0){
                int i=0;
                for(ControlInfoParamVO ci:list){

//                    if(ciparam.getIp().equals(ci.getIp())){
//                        list.remove(i);
//                        break;
//                    }
                    i++;

                }
            }
        }else{
            list=new ArrayList<ControlInfoParamVO>();
        }
        list.add(ciparam);
        setControlInfoList(ciid,list);
        return true;
    }

    public static synchronized  boolean delControlInfoList(String ciid){

        if(null!=fdMap&&fdMap.containsKey(ciid)){
            fdMap.remove(ciid);
            return true;
        }
        return false;
    }

}
