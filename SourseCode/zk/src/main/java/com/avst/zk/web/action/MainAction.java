package com.avst.zk.web.action;


import com.avst.zk.common.cache.AppCache;
import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.DateUtil;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.web.req.GetClientUrlParam;
import com.avst.zk.web.req.LoginParam;
import com.avst.zk.web.service.MainService;
import com.avst.zk.web.vo.GoguidepageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     *
     *
     *
     * 进入用户登录页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/gotologin")
    public ModelAndView gotologin(Model model, HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();



        model.addAttribute("result", rResult);
        model.addAttribute("title", "欢迎进入总控管理系统");

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

    /**
     * 获取服务器状态
     * @return
     */
    @RequestMapping("/getServerStatus")
    @ResponseBody
    public  RResult getServerStatus(){
        RResult result=this.createNewResultOfFail();
        mainService.getServerStatus(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @PostMapping(value = "/logining")
    @ResponseBody
    public RResult checklogin(Model model, HttpServletRequest request, HttpServletResponse response, LoginParam loginParam) {
        RResult result=createNewResultOfFail();
        mainService.logining(result,request,response,loginParam);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public RResult logout(Model model,HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();
        this.changeResultToSuccess(rResult);
        rResult.setMessage("退出成功");
        AppCache.delAppCacheParam();
        UserCache.delUserCache();
//        request.getSession().setAttribute(Constant.MANAGE_USER,null);
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return rResult;
    }

    /**
     * 提交客户端地址
     * @param param
     * @return
     */
    @RequestMapping("/getClientUrl")
    @ResponseBody
    public  RResult getClientUrl(GetClientUrlParam param){
        RResult result=this.createNewResultOfFail();
        mainService.getClientUrl(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 跳转==》主页
     */
    @RequestMapping(value = "/gotomain")
    public ModelAndView gotomain(Model model){
        model.addAttribute("title","总控管理系统");
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

    @RequestMapping(value = "/goguidepage")
    public ModelAndView goguidepage(Model model){
        RResult result=this.createNewResultOfFail();
        GoguidepageVO goguidepageVO = mainService.goguidepage(result);

        model.addAttribute("title",goguidepageVO.getTitle());
        model.addAttribute("client_button_title",goguidepageVO.getClient_button_title());
        model.addAttribute("client_button_url",goguidepageVO.getClient_button_url());
        model.addAttribute("zk_button_title",goguidepageVO.getZk_button_title());
        model.addAttribute("zk_button_url",goguidepageVO.getZk_button_url());
        return  new ModelAndView("sweb/guidepage","goguidepageModel", model);
    }

    @RequestMapping("/getLoginCookie")
    @ResponseBody
    public  RResult getLoginCookie(HttpServletRequest request) {
        RResult result = this.createNewResultOfFail();
        try {
            mainService.getLoginCookie(result,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


}
