package com.avst.zk.web.service;

import com.avst.zk.common.conf.Constant;

import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.web.req.LoginParam;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin123")){
            result.changeToTrue();

//            request.getSession().setAttribute(Constant.MANAGE_USER, loginParam);
            //把账号存到缓存里面
            UserCache.setUserCache(loginParam);

        }else{
            result.setMessage("账号或密码错误！");
        }
        return result;
    }

}
