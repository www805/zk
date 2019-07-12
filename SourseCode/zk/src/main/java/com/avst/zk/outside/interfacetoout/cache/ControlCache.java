package com.avst.zk.outside.interfacetoout.cache;



import com.avst.zk.common.util.DateUtil;
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

    /**
     * 获取服务器信息缓存
     * @param ciid
     * @return
     */
    public static synchronized  List<ControlInfoParamVO> getControlInfoList(String ciid){
        if(null!=fdMap&&fdMap.containsKey(ciid)){
            return fdMap.get(ciid);
        }
        return null;
    }

    /**
     * 通过服务器名字获取服务器信息
     * @param ciid
     * @param servername
     * @return
     */
    public static synchronized ControlInfoParamVO getControlInfoByServername(String ciid, String servername){
        List<ControlInfoParamVO> list=getControlInfoList(ciid);
        if(null!=list&&list.size() > 0){
            for(ControlInfoParamVO ci:list){
                if(servername.equals(ci.getServername())){
                    return ci;
                }
            }
        }
        return null;
    }

    /**
     * 添加服务器信息集合到缓存
     * @param ciid
     * @param cilist
     * @return
     */
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

    /**
     * 添加指定的服务器信息到集合缓存
     * @param ciid
     * @param ciparam
     * @return
     */
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
                        ci.setLasttime(DateUtil.getDateAndMinute());//设置最后连接时间
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
            ciparam.setLasttime(DateUtil.getDateAndMinute());//设置最后连接时间
            list.add(ciparam);
        }
        setControlInfoList(ciid,list);
        return true;
    }

    /**
     * 删除某个服务器集合缓存
     * @param ciid
     * @return
     */
    public static synchronized  boolean delControlInfoList(String ciid){

        if(null!=fdMap&&fdMap.containsKey(ciid)){
            fdMap.remove(ciid);
            return true;
        }
        return false;
    }

}
