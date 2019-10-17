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
import com.avst.zk.feignclient.trm.TrmControl;
import com.avst.zk.feignclient.trm.req.UserloginParam;
import com.avst.zk.outside.interfacetoout.v1.service.ControlInfoService;
import com.avst.zk.web.req.LoginParam;
import com.avst.zk.web.vo.GoguidepageVO;
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

                //拼接引导页url
                String myIP = NetTool.getMyIP();
                Map<String, Object> guidepaMap = (Map<String, Object>) avstYml.get("guidepage");
                Map<String, Object> client_buttonMap = (Map<String, Object>) guidepaMap.get("client_button");
                String url = (String) client_buttonMap.get("url");
                url = "http://" + myIP + url;
                avstYml.put("guidepageUrl", url);

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

            if(StringUtils.isBlank(zk_button_url)){
                zk_button_url = "http://" + hostAddress + ":8079/main/gotologin/";
            }else{
                zk_button_url = "http://" + hostAddress + ":8079" + zk_button_url;
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
}
