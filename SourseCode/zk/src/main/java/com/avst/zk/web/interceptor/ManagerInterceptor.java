package com.avst.zk.web.interceptor;

import com.avst.zk.common.conf.Constant;
import com.avst.zk.common.conf.UserCache;
import com.avst.zk.common.util.LogUtil;
import com.avst.zk.common.util.SpringUtil;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.web.req.LoginParam;
import com.avst.zk.web.service.MainService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员拦截器
 */
public class ManagerInterceptor extends HandlerInterceptorAdapter {

    //在控制器执行前调用
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        LogUtil.intoLog(this.getClass(),"执行preHandle方法-->01");

        //获取session，判断用户
        HttpSession session=request.getSession();

        boolean disbool=true;
//        String forstpageid="/main/goguidepage";//登录界面
        String forstpageid="/main/gotologin";//登录界面
        if (null == UserCache.getUserCache()) {//session.getAttribute(Constant.MANAGE_USER) web客户端session
            disbool = false;
        }

        String url=request.getRequestURI();
        if(url.endsWith("/main/gotologin") || url.endsWith("/main/logining")){//跳过进入登录页面的拦截
            return true;
        }

        //如果跳转的是没路径的，就跳到总控列表页
        if(url.endsWith("/")){
            response.sendRedirect("main/gotomain");
            return false;
        }

//        disbool = true;  //暂时让他成功
        if (disbool) {
            return true;  //通过拦截器，继续执行请求
        } else {//跳转登录界面
//            RResult rresult = new RResult();
//            LoginParam loginParam = new LoginParam();
//            MainService mainService = SpringUtil.getBean(MainService.class);
//            rresult = mainService.logining(rresult, request, response, loginParam);
//            //登录成功就返回true
//            if ("SUCCESS".equalsIgnoreCase(rresult.getActioncode())) {
//                return true;
//            }
            request.getRequestDispatcher(forstpageid).forward(request, response);
            return false;  //没有通过拦截器，返回登录页面
        }
    }
    //在后端控制器执行后调用
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        LogUtil.intoLog(this.getClass(),"执行postHandle方法-->02");
        super.postHandle(request, response, handler, modelAndView);
    }
    //整个请求执行完成后调用
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LogUtil.intoLog(this.getClass(),"执行afterCompletion方法-->03");
        super.afterCompletion(request, response, handler, ex);
    }


}
