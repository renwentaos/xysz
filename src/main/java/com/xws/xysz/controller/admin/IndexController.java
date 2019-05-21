package com.xws.xysz.controller.admin;

import com.xws.xysz.annotations.OperationLog;
import com.xws.xysz.annotations.Token;
import com.xws.xysz.enums.TokenMethod;
import com.xws.xysz.exception.WarnException;
import com.xws.xysz.model.Manager;
import com.xws.xysz.service.ManagerService;
import com.xws.xysz.util.DispatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * JokerYG
 * Date: 2018-11-19
 * Time: 12:02
 */
@Controller("adminIndex")
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    ManagerService managerService;
//
//    @Autowired
//    ACService acService;
//
//    @Autowired
//    TDAPIService tdapiService;



    /**
     * 首页
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "admin/index";
    }


    /**
     * 管理员登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        //清除session
        request.getSession().removeAttribute("adminInfo");
        return "redirect:/alogin";
    }

    /**
     * 进入修改密码页
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password() {
        return "admin/modifyPassword";
    }


    /**
     * 修改密码
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String password(String oldPwd, String newPwd, String newPwd2, Model model, RedirectAttributes attributes) {
        Manager manager = (Manager) request.getSession().getAttribute("adminInfo");
        try {
            managerService.modifyPassword(manager.getId(), oldPwd, newPwd, newPwd2);
            return DispatcherUtil.AlertAndRedirect("修改密码成功", "/admin/index", attributes);

        } catch (WarnException e) {
            model.addAttribute("msg", e.getMessage());
            return "admin/modifyPassword";
        }
    }


        /**
         * 刷新管理员权限
         */
        @RequestMapping(value = "/flush")
        public String flushRights(HttpServletResponse response) {
            String referer = request.getHeader("Referer");
            //获取当前登录的管理员
            Manager manager = (Manager) request.getSession().getAttribute("adminInfo");
            //更新管理员信息
            manager = managerService.flushRights(manager.getId());
            //保存到session
            request.getSession().setAttribute("adminInfo", manager);
            return "redirect:" + referer;
        }
}
