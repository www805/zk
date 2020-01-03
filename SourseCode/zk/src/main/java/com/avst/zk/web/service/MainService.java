package com.avst.zk.web.service;

import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.cache.param.AppCacheParam;

import com.avst.zk.common.conf.Constant;
import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.NetTool;
import com.avst.zk.common.util.OpenUtil;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.util.properties.PropertiesListenerConfig;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.outside.interfacetoout.v1.service.ControlInfoService;
import com.avst.zk.web.req.GetClientUrlParam;
import com.avst.zk.web.req.LoginParam;
import com.avst.zk.web.vo.GetLoginCookieVO;
import com.avst.zk.web.vo.GoguidepageVO;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    private Gson gson = new Gson();

    @Autowired
    private TrmControl trmControl;

    @Autowired
    private ControlInfoService controlInfoService;

    @Value("${nav.file.service}")
    private String application_name;
    @Value("${nav.file.name}")
    private String nav_file_name;

    public RResult logining(RResult result, HttpServletRequest request, HttpServletResponse response, LoginParam loginParam){


        if(StringUtils.isBlank(loginParam.getLoginaccount()) || StringUtils.isBlank(loginParam.getPassword())){
            result.setMessage("账号或密码不能为空");
            return result;
        }


        String loginaccount = "";
        //獲取trm帳號密碼信息
//        LogUtil.intoLog(1, this.getClass(), "远程请求trm看是否已经登陆成功，如果成功就不走zk登录");
//
//        LogUtil.intoLog(3, this.getClass(), "获取trm账号密码失败，执行zk登录形式");
        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
            RResult rr = new RResult();
            this.getNavList(rr);
        }

        /**取出账号密码**/
        Map<String, Object> loginData = cacheParam.getData();

        loginaccount = (String) loginData.get("loginaccount");
        String password = (String) loginData.get("password");

        if(!loginParam.getLoginaccount().equals(loginaccount)){
            result.setMessage("用户不存在");
            return result;
        }

        if(!loginParam.getPassword().equals(password)){
            result.setMessage("用户名或密码错误");
            return result;
        }

//        ReqParam reqParam = new ReqParam();
//        RResult userPwdResult = null;
//        try {
//            userPwdResult = trmControl.getUserPwd(reqParam);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.intoLog(4, this.getClass(), "远程请求trm获取账号密码失败。。。");
//        }
//
//        if(null == userPwdResult || "FAIL".equalsIgnoreCase(userPwdResult.getActioncode())){
//
//        }else{
//            ControlInfoParamVO vo = gson.fromJson(gson.toJson(userPwdResult.getData()), ControlInfoParamVO.class);
//
//            loginaccount = vo.getLoginusername();
//            loginParam.setLoginaccount(loginaccount);
//            loginParam.setPassword(vo.getLoginpassword());
//            LogUtil.intoLog(1, this.getClass(), "获取trm账号密码成功！账号：" + loginParam.getLoginaccount() + " 密码：" + loginParam.getPassword());
//        }

        boolean rememberpassword=loginParam.isRememberpassword();
        if (rememberpassword){
            Cookie zkloginaccount=new Cookie("ZKLOGINACCOUNT",loginaccount);
            zkloginaccount.setMaxAge(60*60*24*7);
            zkloginaccount.setPath("/");
            Cookie zkrememberme=new Cookie("ZKREMEMBERME","YES");
            zkrememberme.setMaxAge(60*60*24*7);
            zkrememberme.setPath("/");
            response.addCookie(zkloginaccount);
            response.addCookie(zkrememberme);
        }else {
            Cookie zkloginaccount=new Cookie("ZKLOGINACCOUNT",null);
            zkloginaccount.setMaxAge(0);
            zkloginaccount.setPath("/");
            Cookie zkrememberme=new Cookie("ZKREMEMBERME",null);
            zkrememberme.setMaxAge(0);
            zkrememberme.setPath("/");
            response.addCookie(zkrememberme);
            response.addCookie(zkrememberme);
        }

        result.changeToTrue();
        request.getSession().setAttribute(Constant.MANAGE_USER, loginParam);
        /**把账号存到缓存里面**/
        UserCache.setUserCache(loginParam);

        return result;
    }

    public void getNavList(RResult result) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        result.setData(cacheParam);
        result.changeToTrue();

    }

    public void getServerStatus(RResult result) {
        controlInfoService.getControlInfo(result);
    }


    public GoguidepageVO goguidepage(RResult result) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
            this.getNavList(result);
            cacheParam = AppCache.getAppCacheParam();
        }
        Map<String, Object> map = cacheParam.getData();

        String title = "";
        String client_button_title = "";
        String client_button_url = "";
        String zk_button_title = "";
        String zk_button_url = "";
        if (null != map) {
            Map<String, Object> guidepagemap = (Map<String, Object>) map.get("guidepage");
            Map<String, Object> client_button = (Map<String, Object>) guidepagemap.get("client_button");
            Map<String, Object> zk_button = (Map<String, Object>) guidepagemap.get("zk_button");

            title = (String) guidepagemap.get("title");
            client_button_title = (String) client_button.get("title");
            client_button_url = (String) client_button.get("url");
            zk_button_title = (String) zk_button.get("title");
            zk_button_url = (String) zk_button.get("url");

            //获取本机ip地址
            String hostAddress = NetTool.getMyIP();
            if(StringUtils.isBlank(hostAddress)){
                hostAddress = "localhost";
            }

            String port= PropertiesListenerConfig.getProperty("spring.port");
            if(StringUtils.isBlank(port)){
                port = "6059";
            }
            if(StringUtils.isBlank(zk_button_url)){
                zk_button_url = "http://" + hostAddress + ":" + port + "/main/gotologin/";
            }else{
                zk_button_url = "http://" + hostAddress + ":" + port + zk_button_url;
            }
        }else{
            LogUtil.intoLog(4, this.getClass(), "外部配置文件读取出错！！！！" );
        }

        GoguidepageVO goguidepageVO = new GoguidepageVO();
        goguidepageVO.setTitle(title);
        goguidepageVO.setClient_button_title(client_button_title);
        goguidepageVO.setClient_button_url(client_button_url);
        goguidepageVO.setZk_button_title(zk_button_title);
        goguidepageVO.setZk_button_url(zk_button_url);

        return goguidepageVO;
    }

    public void getClientUrl(RResult result, GetClientUrlParam param) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        Map<String, Object> data = cacheParam.getData();
        data.put("guidepageUrl", param.getClientUrl());

        result.changeToTrue();

    }

    public void getLoginCookie(RResult result,HttpServletRequest request){
        GetLoginCookieVO vo=new GetLoginCookieVO();
        String loginaccount = "";
        String password = "";

        //获取当前站点的所有Cookie
        String rememberme=null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
                if ("ZKLOGINACCOUNT".equals(cookies[i].getName())) {
                    loginaccount = cookies[i].getValue();
                } else if ("ZKREMEMBERME".equals(cookies[i].getName())) {
                    rememberme = cookies[i].getValue();
                }
            }
        }

        if (StringUtils.isNotEmpty(rememberme)&&rememberme.equals("YES")&&StringUtils.isNotEmpty(loginaccount)){
            AppCacheParam cacheParam = AppCache.getAppCacheParam();
            if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
                RResult rr = new RResult();
                this.getNavList(rr);
            }

            Map<String, Object> loginData = cacheParam.getData();
            password = (String) loginData.get("password");
        }


        vo.setLoginaccount(loginaccount);
        vo.setPassword(password);
        result.setData(vo);
        result.changeToTrue();
        return;
    }
}
