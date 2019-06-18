package com.avst.zk.feignclient.ec;


import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.feignclient.base.ClientResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(value = "ec", url = "localhost:8081/", fallback = ClientResult.class)
public interface EquipmentControl {

    @RequestMapping("/getControlInfo")
    @ResponseBody
    public ControlInfoParamVO getControlInfo();

}
