package com.xws.xysz.controller.admin;

import com.github.pagehelper.PageInfo;
import com.xws.xysz.annotations.OperationLog;
import com.xws.xysz.annotations.Token;
import com.xws.xysz.enums.TokenMethod;
import com.xws.xysz.exception.WarnException;
import com.xws.xysz.model.Manager;
import com.xws.xysz.model.Role;
import com.xws.xysz.service.ManagerService;
import com.xws.xysz.util.DispatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * JokerYG
 * Date: 2018-11-21
 * Time: 12:15
 */
@Controller("managerController")
@RequestMapping("/admin/manager/")
public class ManagerController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ManagerService managerService;


    /**
     * 查询管理员列表
     */
    @RequestMapping(value = "/{pageNo}-{pageSize}", method = RequestMethod.GET)
    public String list(@PathVariable String pageNo, @PathVariable String pageSize, Model model) {
        PageInfo pageInfo = managerService.searchManager(pageNo, pageSize, null);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/manager/list";
    }


    /**
     * 进入添加管理员页
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "admin/manager/add";
    }

    /**
     * 添加管理员
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "添加管理员", arguDesc = {"管理员信息"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Manager manager, RedirectAttributes attributes, Model model) {
        try {
            managerService.addManager(manager);
            return DispatcherUtil.AlertAndRedirect("添加管理员成功", "/admin/manager/1-10", attributes);
        } catch (WarnException e) {
            model.addAttribute("msg", e.getMessage());
            return "admin/manager/add";
        }
    }

    /**
     * 进入分配角色页面
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/role/allot", method = RequestMethod.GET)
    public String allotRole(@RequestParam int id, Model model) {

        Manager manager = managerService.getManagerById(id);
        List<Role> list = managerService.getAllRoles();

        model.addAttribute("list", list);
        model.addAttribute("manager", manager);

        return "admin/manager/allotRole";
    }


    /**
     * 分配角色
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "分配角色", arguDesc = {"管理员信息"})
    @RequestMapping(value = "/role/allot", method = RequestMethod.POST)
    public String allotRole(Manager manager, RedirectAttributes model, RedirectAttributes attributes) {
        try {
            managerService.modifyManager(manager);
            return DispatcherUtil.AlertAndRedirect("修改管理员信息成功", "/admin/manager/1-10", attributes);
        } catch (WarnException e) {
            model.addFlashAttribute("msg", e.getMessage());
            return "redirect:/admin/manager/role/allot?id=" + manager.getId();
        }
    }

    /**
     * 角色列表
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String roleList(Model model) {
        List<Role> list = managerService.getAllRoles();
        model.addAttribute("list", list);
        return "admin/manager/roleList";
    }

    /**
     * 进入添加角色页面
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/role/add", method = RequestMethod.GET)
    public String addRole(Model model) {
        List<String> list = managerService.getAllRights(null);
        model.addAttribute("treeJsons", list);
        return "admin/manager/addRole";
    }

    /**
     * 添加角色
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "添加角色", arguDesc = {"角色信息"})
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public String addRole(Role role, RedirectAttributes model, RedirectAttributes attributes) {
        try {
            managerService.addRole(role);
            return DispatcherUtil.AlertAndRedirect("添加角色成功", "/admin/manager/roles", attributes);
        } catch (WarnException e) {
            model.addFlashAttribute("msg", e.getMessage());
            return "redirect:/admin/manager/role/add";
        }
    }


    /**
     * 进入角色分配权限页面
     */
    @Token(method = TokenMethod.create)
    @RequestMapping(value = "/role/rights", method = RequestMethod.GET)
    public String allotPermission(int id, Model model) {
        Role role = managerService.getRoleById(id);
        List<String> treeJsons = managerService.getAllRights(role.getRights());
        model.addAttribute("treeJsons", treeJsons);
        model.addAttribute("role", role);

        return "admin/manager/allotRights";
    }


    /**
     * 给角色分配权限
     */
    @Token(method = TokenMethod.verify)
    @OperationLog(desc = "分配权限", arguDesc = {"角色信息"})
    @RequestMapping(value = "/role/rights", method = RequestMethod.POST)
    public String allotPermission(Role role, RedirectAttributes model, RedirectAttributes attributes) {
        try {
            managerService.modifyRole(role);
            return DispatcherUtil.AlertAndRedirect("修改角色信息成功", "/admin/manager/roles", attributes);

        } catch (WarnException e) {
            model.addFlashAttribute("msg", e.getMessage());
            return "redirect:/admin/manager/role/rights?id=" + role.getId();
        }
    }

}
