package com.xws.xysz.service;

import com.github.pagehelper.PageInfo;
import com.xws.xysz.exception.WarnException;
import com.xws.xysz.model.Manager;
import com.xws.xysz.model.Role;

import java.util.List;
import java.util.Map;

/**
 * JokerYG
 * Date: 2018-11-06
 * Time: 9:31
 */
public interface ManagerService {

    /**
     * 管理员登录
     *
     * @param username    用户名
     * @param pwd         密码
     * @param imgCode     填写的图片验证码(当登陆次数大于三次时使用)
     * @param count       登录次数(大于3次需要验证图形验证码)
     * @param realImgCode 实际图片验证码(当登陆次数大于三次时使用)
     * @return 管理员实体
     */
    Manager login(String username, String pwd, String imgCode, int count, Object realImgCode) throws WarnException;



    /**
     * 修改密码
     *
     * @param managerId 管理员id
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @param newPwd2   确认新密码
     */
    void modifyPassword(int managerId, String oldPwd, String newPwd, String newPwd2) throws WarnException;


    /**
     * 获取最新的管理员信息刷新权限
     */
    Manager flushRights(int managerId);

    /**
     * 添加管理员
     *
     * @param manager 管理员信息
     * @throws WarnException
     */
    void addManager(Manager manager) throws WarnException;

    /**
     * 根据条件搜索管理员列表
     *
     * @param sta  状态,可为空
     * @param no   页码
     * @param size 每页显示数量
     */
    PageInfo searchManager(String no, String size, String sta);

    /**
     * 根据id获取管理员信息
     */
    Manager getManagerById(Integer id);

    /**
     * 修改管理员
     *
     * @param manager manager信息
     * @throws WarnException
     */
    void modifyManager(Manager manager) throws WarnException;


    /**
     * 查看全部权限列表-json
     * @param idMap 需要直接勾选的id MAP(key-id)
     * @return 权限列表
     */
    List<String> getAllRights(Map<String, String> idMap);

    /**
     * 增加角色
     *
     * @param role 角色信息
     * @throws WarnException
     */
    void addRole(Role role) throws WarnException;


    /**
     * 获得全部角色
     */
    List<Role> getAllRoles();

    /**
     * 根据id获取角色信息
     *
     * @param id
     * @return
     */
    Role getRoleById(int id);


    /**
     * 修改角色拥有权限
     *
     * @param role 角色
     * @throws WarnException
     */
    void modifyRole(Role role) throws WarnException;
}
