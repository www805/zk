package com.avst.zk.feignclient.trm;

import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@FeignClient(name = "trm", url = "localhost:8080/")
public interface TrmControl {

    //提供给总控的登录账号密码
    @RequestMapping("/trm/v1/getLoginUser")
    public RResult getLoginUser(@RequestBody ReqParam<UserloginParam> param);
}