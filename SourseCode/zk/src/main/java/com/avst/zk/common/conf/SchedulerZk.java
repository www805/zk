package com.avst.zk.common.conf;

import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时器任务
 * 注意：这种定时器一定要用try包一下，以免内存泄露或者线程异常不能释放
 */
@Component
public class SchedulerZk {

    /**
     * 2分钟跳一次
     * 0 0/1 * * * ?
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTasks() {
        //清空缓存
//        ControlCache.delControlInfoList("list");
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");
        if (null != list && list.size() > 0) {
            for (ControlInfoParamVO paramVO : list) {
                //判断时间如果2分钟没连接就设置为断线状态
                int i = calLastedTime(paramVO.getLasttime());
                if (i >= 120) {
                    paramVO.setStatus(0);
                }
            }
        }
        LogUtil.intoLog("检测所有服务心跳，如果2分钟没上报，状态设置为断开");
//        System.out.println("设置服务状态为断开");
    }

    //判断两个时间相差几秒
    public  int calLastedTime(String lasttime) {
        long a = new Date().getTime();

        DateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = null;
        try {
            parse = formatter.parse(lasttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long b = parse.getTime();
        int c = (int)((a - b) / 1000);return c;
    }
}
