package com.avst.zk.feignclient.ec;


import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.feignclient.base.ClientResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(value = "ec", url = "localhost:8081/", fallback = ClientResult.class)
public interface EquipmentControl {

    @RequestMapping("/ec/v1/checkClient")
    @ResponseBody
    public RResult checkClient(@RequestBody ReqParam param);

}
