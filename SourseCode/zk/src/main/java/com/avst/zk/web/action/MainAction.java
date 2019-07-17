package com.avst.zk.web.action;


import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.conf.Constant;
import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.web.req.LoginParam;
import com.avst.zk.web.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/main")
public class MainAction extends BaseAction {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/{pageid}")
    public ModelAndView gotomain(Model model, @PathVariable("pageid")String pageid) {
        return new ModelAndView(pageid, pageid, model);
    }

    /**
     * 进入用户登录页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/gotologin")
    public ModelAndView gotologin(Model model, HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();



        model.addAttribute("result", rResult);
        model.addAttribute("title", "欢迎进入AVST总控管理系统");

//        request.getSession().setAttribute(Constant.MANAGE_WEB,null);

        return new ModelAndView("sweb/login", "login", model);
    }

    /**
     * 获取导航栏目
     * @return
     */
    @RequestMapping("/getNavList")
    @ResponseBody
    public  RResult getNavList(){
        RResult result=this.createNewResultOfFail();
        mainService.getNavList(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @PostMapping(value = "/logining")
    @ResponseBody
    public RResult checklogin(Model model, HttpServletRequest request, LoginParam loginParam) {
        RResult result=createNewResultOfFail();
        mainService.logining(result,request,loginParam);
        AppCache.delAppCacheParam();
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public RResult logout(Model model,HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();
        this.changeResultToSuccess(rResult);
        rResult.setMessage("退出成功");
        UserCache.delUserCache();
//        request.getSession().setAttribute(Constant.MANAGE_USER,null);
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return rResult;
    }

    /**
     * 跳转==》主页
     */
    @RequestMapping(value = "/gotomain")
    public ModelAndView gotomain(Model model){
        model.addAttribute("title","AVST总控管理系统");
        //把用户名传到页面上
        String username = "";
        if(null != UserCache.getUserCache()){
            username = UserCache.getUserCache().getUsername();
        }
        model.addAttribute("username", username);

        return  new ModelAndView("sweb/main","mainModel", model);
    }

    /**
     * 跳转==》主页
     */
    @RequestMapping(value = "/gotocontrol")
    public ModelAndView gotohome(Model model){
        model.addAttribute("title","监控列表");
        return  new ModelAndView("sweb/control/controlList","controlListModel", model);
    }



}
