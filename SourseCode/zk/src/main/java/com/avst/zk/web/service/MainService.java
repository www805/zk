package com.avst.zk.web.service;

import com.avst.zk.common.conf.Constant;

import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import com.avst.zk.web.req.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    @Autowired
    private TrmControl trmControl;

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        try {
            //把账号密码放到参数内
            UserloginParam userloginParam = new UserloginParam();
            userloginParam.setLoginaccount(loginParam.getLoginaccount());
            userloginParam.setPassword(loginParam.getPassword());

            ReqParam<UserloginParam> reqParam = new ReqParam();
            reqParam.setParam(userloginParam);

            //远程请求登录
            RResult loginResult = trmControl.getLoginUser(reqParam);

            if ("SUCCESS".equalsIgnoreCase(loginResult.getActioncode())) {
                result.changeToTrue();
//              request.getSession().setAttribute(Constant.MANAGE_USER, loginParam);
                //把账号存到缓存里面
                UserCache.setUserCache(loginParam);
            }else{
                result.setMessage(loginResult.getMessage());
            }
        } catch (Exception e) {
            result.setMessage("远程登录请求失败，请启动业务系统后重试");
            LogUtil.intoLog(4,this.getClass(),"logining TrmControl。run 远程登录，请求失败");
        }

        return result;
    }

}
