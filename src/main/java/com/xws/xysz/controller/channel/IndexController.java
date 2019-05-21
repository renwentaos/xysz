package com.xws.xysz.controller.channel;


import com.github.pagehelper.PageInfo;
import com.xws.xysz.annotations.OperationLog;
import com.xws.xysz.annotations.Token;
import com.xws.xysz.enums.TokenMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * JokerYG
 * Date: 2018-11-19
 * Time: 12:02
 */
@Controller("channelIndex")
@RequestMapping("/channel")
public class IndexController {
    @Autowired
    HttpServletRequest request;

//    @Autowired
//    ChannelService channelService;
//
//    @Autowired
//    ChannelReportService channelReportService;

    /**
     * 首页
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "channel/index";
    }


    /**
     * 登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        //清除session
        request.getSession().removeAttribute("channelInfo");
        return "redirect:/clogin";
    }

    /**
     * 进入修改密码页
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    private String password() {
        return "channel/modifyPassword";
    }


    /**
     * 修改密码
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String password(String oldPwd, String newPwd, String newPwd2, Model model, RedirectAttributes attributes) {
//        Channel channel = (Channel) request.getSession().getAttribute("channelInfo");
//        try {
//            channelService.modifyPassword(channel.getId(), oldPwd, newPwd, newPwd2);
//            return DispatcherUtil.AlertAndRedirect("修改密码成功", "/channel/index", attributes);
//
//        } catch (WarnException e) {
//            model.addAttribute("msg", e.getMessage());
            return "channel/modifyPassword";
//        }
    }

    /**
     * 渠道商统计
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param model
     * @return
     */
    @RequestMapping(value = "/report/{pageNo}-{pageSize}",method = RequestMethod.GET)
    public String list(@PathVariable String pageNo, @PathVariable String pageSize, String startTime, String endTime, Model model) {
//        Channel channel = (Channel) request.getSession().getAttribute("channelInfo");
//        PageInfo pageInfo = channelReportService.searchList(pageNo, pageSize, startTime, endTime,channel.getId(),null);
//        model.addAttribute("pageInfo", pageInfo);
        return "channel/list";
    }

}
