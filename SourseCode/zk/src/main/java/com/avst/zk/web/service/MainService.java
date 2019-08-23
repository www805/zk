package com.avst.zk.web.service;

import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.cache.param.AppCacheParam;

import com.avst.zk.common.conf.Constant;
import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.OpenUtil;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import com.avst.zk.outside.interfacetoout.v1.service.ControlInfoService;
import com.avst.zk.web.req.LoginParam;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private ControlInfoService controlInfoService;

    @Value("${nav.file.service}")
    private String application_name;
    @Value("${nav.file.name}")
    private String nav_file_name;

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
            RResult rr = new RResult();
            this.getNavList(rr);
        }

        /**取出账号密码**/
        Map<String, Object> loginData = cacheParam.getData();

        String loginaccount = (String) loginData.get("loginaccount");
        String password = (String) loginData.get("password");

        if(!loginParam.getLoginaccount().equals(loginaccount)){
            result.setMessage("用户不存在");
            return result;
        }

        if(!loginParam.getPassword().equals(password)){
            result.setMessage("用户名或密码错误");
            return result;
        }

        result.changeToTrue();
        request.getSession().setAttribute(Constant.MANAGE_USER, loginParam);
        /**把账号存到缓存里面**/
        UserCache.setUserCache(loginParam);


//        try {
//            //把账号密码放到参数内
//            UserloginParam userloginParam = new UserloginParam();
//            userloginParam.setLoginaccount(loginParam.getLoginaccount());
//            userloginParam.setPassword(loginParam.getPassword());
//
//            ReqParam<UserloginParam> reqParam = new ReqParam();
//            reqParam.setParam(userloginParam);
//
//            //远程请求登录
//            RResult loginResult = trmControl.getLoginUser(reqParam);
//
//            if ("SUCCESS".equalsIgnoreCase(loginResult.getActioncode())) {
//                result.changeToTrue();
////              request.getSession().setAttribute(Constant.MANAGE_USER, loginParam);
//                //把账号存到缓存里面
//                UserCache.setUserCache(loginParam);
//            }else{
//                result.setMessage(loginResult.getMessage());
//            }
//        } catch (Exception e) {
//            result.setMessage("远程登录请求失败，请启动业务系统后重试");
//            LogUtil.intoLog(4,this.getClass(),"logining TrmControl。run 远程登录，请求失败");
//        }

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
                avstYml.put("bottom", map.get("bottom"));
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

    public void getServerStatus(RResult result) {
        controlInfoService.getControlInfo(result);
    }
}
