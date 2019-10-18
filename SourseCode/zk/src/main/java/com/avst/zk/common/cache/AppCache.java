package com.avst.zk.common.cache;


import com.avst.zk.common.cache.param.AppCacheParam;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.NetTool;
import com.avst.zk.common.util.OpenUtil;
import com.avst.zk.common.util.properties.PropertiesListenerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class AppCache {

    private static String application_name= PropertiesListenerConfig.getProperty("nav.file.service");
    private static String nav_file_name= PropertiesListenerConfig.getProperty("nav.file.name");

    private static AppCacheParam appCacheParam;

    public static synchronized AppCacheParam getAppCacheParam() {

        if(null == appCacheParam||null==appCacheParam.getData()){
            appCacheParam = new AppCacheParam();
            initAppCacheParam();
        }
        return appCacheParam;
    }

    public static synchronized void setAppCacheParam(AppCacheParam appCacheParam) {
        AppCache.appCacheParam = appCacheParam;
    }

    public static synchronized void delAppCacheParam(){
        appCacheParam = null;
    }


    private static synchronized void initAppCacheParam(){

        String path = OpenUtil.getXMSoursePath() + "\\" + nav_file_name + ".yml";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);

            Yaml yaml = new Yaml();
            Map<String,Object> map = yaml.load(fis);
            Map<String,Object> avstYml = (Map<String, Object>) map.get(application_name);
            avstYml.put("bottom", map.get("bottom"));
            if (null != map && map.size() > 0) {
                appCacheParam.setTitle((String) avstYml.get("title"));
            }

            //拼接引导页url
            String myIP = NetTool.getMyIP();
            Map<String, Object> guidepaMap = (Map<String, Object>) avstYml.get("guidepage");
            Map<String, Object> client_buttonMap = (Map<String, Object>) guidepaMap.get("client_button");
            String url = (String) client_buttonMap.get("url");
            url = "http://" + myIP + url;
            avstYml.put("guidepageUrl", url);

            appCacheParam.setData(avstYml);

        } catch (IOException e) {
            LogUtil.intoLog(4, AppCache.class, "没找到外部配置文件：" + path);
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
}
