package com.avst.zk.web.service;

import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.cache.param.AppCacheParam;

import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.OpenUtil;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import com.avst.zk.web.req.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    private TrmControl trmControl;

    @Value("${nav.file.service}")
    private String application_name;
    @Value("${nav.file.name}")
    private String nav_file_name;

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

    public void getNavList(RResult result) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if(null == cacheParam.getData()){
            String path = OpenUtil.getXMSoursePath() + "\\" + nav_file_name + ".yml";
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);

                Yaml yaml = new Yaml();
                Map<String,Object> map = yaml.load(fis);
                Map<String,Object> avstYml = (Map<String, Object>) map.get(application_name);
                if (null != map && map.size() > 0) {
                    cacheParam.setTitle((String) avstYml.get("title"));
                }
                cacheParam.setData(avstYml);

            } catch (IOException e) {
                LogUtil.intoLog(4, this.getClass(), "没找到外部配置文件：" + path);
            }finally {
                if(null != fis){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        result.setData(cacheParam);
        result.changeToTrue();

    }
}
