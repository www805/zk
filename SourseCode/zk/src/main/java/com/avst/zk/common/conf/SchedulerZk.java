package com.avst.zk.common.conf;

import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时器任务
 * 注意：这种定时器一定要用try包一下，以免内存泄露或者线程异常不能释放
 */
@Component
public class SchedulerZk {
    //每个小时的第五分钟执行

    /**
     * 1分钟心跳一次
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTasks() {
        //清空缓存
//        ControlCache.delControlInfoList("list");
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");
        if (null != list && list.size() > 0) {
            for (ControlInfoParamVO paramVO : list) {
                paramVO.setStatus(0);
            }
        }
    }
}
