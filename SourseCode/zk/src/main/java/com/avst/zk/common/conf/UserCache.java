package com.avst.zk.common.conf;

import com.avst.zk.web.req.LoginParam;

public class UserCache{

    private static LoginParam userCache;

    //查
    public static synchronized  LoginParam getUserCache(){
        return userCache;
    }

    //增/改
    public static synchronized  boolean setUserCache(LoginParam user){
        userCache = user;
        return true;
    }

    //删
    public static synchronized  boolean delUserCache(){
        userCache = null;
        return true;
    }


}
