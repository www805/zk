package com.avst.zk.outside.interfacetoout.cache;



import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.sun.org.apache.xpath.internal.operations.Bool;

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
        Boolean addStu = false;
        if(null!=list){
            if(list.size() > 0){
                int i=0;
                for(ControlInfoParamVO ci:list){

                    if(ciparam.getServername().equals(ci.getServername())){
//                        list.remove(i);
                        ci.setUse_item(ciparam.getUse_item());
                        ci.setTotal_item(ciparam.getTotal_item());
                        ci.setStatus(ciparam.getStatus());
//                        ci.setCreatetime(ciparam.getCreatetime());
                        ci.setServername(ciparam.getServername());
                        addStu = true;
                        break;
                    }
                    i++;

                }
            }
        }else{
            list=new ArrayList<ControlInfoParamVO>();
        }
        if (addStu == false) {
            ciparam.setCreatetime(DateUtil.getDateAndMinute());//设置当前时间
            list.add(ciparam);
        }
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
