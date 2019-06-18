package com.avst.zk.feignclient.trm;

import com.avst.zk.feignclient.base.ClientResult;
import com.avst.zk.common.vo.ControlInfoParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(value = "trm", url = "localhost:8080/", fallback = ClientResult.class)
public interface TrmControl {

    @RequestMapping("/getControlInfo")
    @ResponseBody
    public ControlInfoParamVO getControlInfo();

}
