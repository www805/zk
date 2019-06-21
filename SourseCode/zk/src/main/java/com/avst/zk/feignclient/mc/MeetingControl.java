package com.avst.zk.feignclient.mc;

import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ToOutVO;
import com.avst.zk.feignclient.base.ClientResult;
import com.avst.zk.common.vo.ControlInfoParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备控制的代理
 */
@FeignClient(value = "mc", url = "localhost:8082/", fallback = ClientResult.class)
public interface MeetingControl {

    @RequestMapping("/mt/v1/checkClient")
    @ResponseBody
    public RResult<ToOutVO> checkClient(@RequestBody ReqParam param);

}
