package com.avst.zk.action;

import com.avst.zk.common.util.baseaction.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainAction extends BaseAction {
//    @Autowired
//    private MainService mainService;

    @RequestMapping(value = "/{pageid}")
    public ModelAndView gotomain(Model model, @PathVariable("pageid")String pageid) {
        return new ModelAndView(pageid, pageid, model);
    }


    /**
     * 跳转==》登陆页
     */
    @RequestMapping(value = "/gotologin")
    public ModelAndView gotologin(Model model){
        model.addAttribute("title","欢迎使用总控管理系统");
        return  new ModelAndView("client_web/base/login","loginModel", model);
    }

    /**
     * 跳转==》主页
     */
    @RequestMapping(value = "/gotomain")
    public ModelAndView gotomain(Model model){
        model.addAttribute("title","AVST总控管理系统");
        return  new ModelAndView("base/main","mainModel", model);
    }

    /**
     * 跳转==》主页
     */
    @RequestMapping(value = "/gotocontrol")
    public ModelAndView gotohome(Model model){
        model.addAttribute("title","监控列表");
        return  new ModelAndView("control/controlList","controlListModel", model);
    }



}
