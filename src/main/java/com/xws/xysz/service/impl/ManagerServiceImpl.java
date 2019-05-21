package com.xws.xysz.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xws.xysz.dao.ManagerDao;
import com.xws.xysz.dao.RightsDao;
import com.xws.xysz.dao.RoleDao;
import com.xws.xysz.enums.StateEnum;
import com.xws.xysz.exception.ErrorException;
import com.xws.xysz.exception.WarnException;
import com.xws.xysz.model.Manager;
import com.xws.xysz.model.Rights;
import com.xws.xysz.model.Role;
import com.xws.xysz.model.TreeNode;
import com.xws.xysz.service.ManagerService;
import com.xws.xysz.util.PageUtil;
import com.xws.xysz.util.PasswordEncoder;
import com.xws.xysz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 杨光
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerDao managerDao;

    @Autowired
    RightsDao rightsDao;

    @Autowired
    RoleDao roleDao;

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
    @Override
    public Manager login(String username, String pwd, String imgCode, int count, Object realImgCode) throws WarnException {
        try {
            if (StringUtil.isEmpty(false,username,pwd)) {
                throw new WarnException("用户名密码不能为空");
            }
            if (count >= 3) {
                if (realImgCode == null || !imgCode.toLowerCase().equals(realImgCode.toString().toLowerCase())) {
                    throw new WarnException("图片验证码不正确");
                }
            }

            //根据用户名获得实体
            Manager manager = managerDao.selectByUsername(username);
            if (manager == null) {
                throw new WarnException("用户名或密码错误");
            }
            if (manager.getState() == StateEnum.Basic.冻结.getCode()) {
                throw new WarnException("管理员已被冻结");
            }
            if (!PasswordEncoder.isPasswordValid(manager.getPassword(), pwd, manager.getPKey())) {
                throw new WarnException("用户名或密码错误");
            }
            manager =  managerDao.selectManagerAndRightsById(manager.getId());
            //生成树结构权限列表
            manager.createTreeList();
            return manager;
        } catch (NoSuchAlgorithmException e) {
            throw new ErrorException("管理登陆密码验证出错", e);
        } catch (InvalidKeySpecException e) {
            throw new ErrorException("管理登陆密码验证出错", e);
        }

    }



    /**
     * 修改密码
     *
     * @param managerId 管理员id
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @param newPwd2   确认新密码
     */
    @Override
    public void modifyPassword(int managerId, String oldPwd, String newPwd, String newPwd2) throws WarnException {
        try {
            String msg = "";
            Manager manager;
            //验证信息
            if (oldPwd == null || oldPwd.trim().length() < 5) {
                msg = "输入的旧密码不正确";
            } else if (newPwd == null || newPwd.trim().length() < 5) {
                msg = "输入的新密码不正确";
            } else if (!newPwd.equals(newPwd2)) {
                msg = "两次新密码输入的不一致！";
            } else {
                //判断原始密码是否正确

                //根据id得到会员信息
                manager = managerDao.selectById(managerId);
                if (manager == null) {
                    throw new ErrorException("管理员信息不存在", managerId);
                } else if (!PasswordEncoder.isPasswordValid(manager.getPassword(), oldPwd, manager.getPKey())) {
                    msg = "旧密码输入错误";
                }
            }

            if (msg.length() > 0) {
                //数据校验错误
                throw new WarnException(msg);
            }


            //获得扰码，与加密后的密码
            String salt = PasswordEncoder.getSalt();
            String pwd = PasswordEncoder.encode(newPwd, salt);
            //设定新的扰码与密码
            Manager newManager = new Manager();
            newManager.setId(managerId);
            newManager.setPKey(salt);
            newManager.setPassword(pwd);
            //更新用户信息
            int row = managerDao.updateById(newManager);
            if (row <= 0) {
                //重置密码失败
                throw new ErrorException("修改密码时信息提交失败，返回行数：" + row + "行", newPwd);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new ErrorException("修改密码时密码加密出错", e);
        } catch (InvalidKeySpecException e) {
            throw new ErrorException("修改密码时密码加密出错", e);
        }
    }

    /**
     * 获取最新的管理员信息刷新权限
     */
    @Override
    public Manager flushRights(int managerId) {
        Manager manager = managerDao.selectManagerAndRightsById(managerId);
        manager.createTreeList();
        return manager;
    }

    /**
     * 添加管理员
     *
     * @param manager 管理员信息
     * @throws WarnException
     */
    @Override
    public void addManager(Manager manager) throws WarnException {
        if (manager == null) {
            throw new WarnException("数据不能为空");
        }
        if (StringUtil.isEmpty(manager.getUserName())) {
            throw new WarnException("用户名不能为空");
        }
        if (StringUtil.isEmpty(manager.getName())) {
            throw new WarnException("真实姓名不能为空");
        }
        if (StringUtil.isEmpty(manager.getPassword())) {
            throw new WarnException("密码不能为空");
        }

        //设置扰码
        try {
            manager.setPKey(PasswordEncoder.getSalt());
            manager.setPassword(PasswordEncoder.encode(manager.getPassword(), manager.getPKey()));
            manager.setState(StateEnum.Basic.正常.getCode());

            int row = managerDao.insert(manager);
            if (row == 0) {
                throw new ErrorException("添加管理员提交失败，返回行数：" + row + "行", manager);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new ErrorException("添加管理员时密码加密出错", e);
        } catch (InvalidKeySpecException e) {
            throw new ErrorException("添加管理员时密码加密出错", e);
        }
    }


    /**
     * 根据条件搜索管理员列表
     *
     * @param no   页码
     * @param size 每页显示数量
     * @param sta  状态,可为空
     */
    @Override
    public PageInfo searchManager(String no, String size, String sta) {

        PageUtil page = PageUtil.pageHandle(no,size);

        //查询
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<Manager> list = managerDao.selectAll(sta);

        return new PageInfo(list);
    }

    /**
     * 根据id获取管理员信息
     *
     * @param id
     * @return
     */
    @Override
    public Manager getManagerById(Integer id) {
        return managerDao.selectById(id);
    }

    /**
     * 修改管理员
     *
     * @param manager 管理员id
     * @throws WarnException
     */
    @Override
    public void modifyManager(Manager manager) throws WarnException {

        if (manager == null) {
            throw new WarnException("数据不能为空");
        }

        if (StringUtil.isEmpty(manager.getUserName())) {
            throw new WarnException("用户名不能为空");
        }

        if (StringUtil.isEmpty(manager.getName())) {
            throw new WarnException("真实姓名不能为空");
        }

        int row = managerDao.updateById(manager);
        if (row == 0) {
            throw new ErrorException("修改管理员出错", manager);
        }
    }

    /**
     * 查看全部权限列表-json
     *
     * @param idMap 需要直接勾选的id MAP(key-id)
     * @return 权限列表-json
     */
    @Override
    public List<String> getAllRights(Map<String, String> idMap) {
        //获得数据
        List<TreeNode> list = rightsToTreeNodes(rightsDao.selectAll());

        //按照子父级组合
        List<TreeNode> newList = new ArrayList<>();
        for (TreeNode tree : list) {
            if (tree.getFatherId() == 0) {
                tree.setNumber(newList.size());
                newList.add(tree);
            }
            for (TreeNode t2 : list) {
                if (t2.getFatherId() == tree.getCheckboxValue()) {
                    t2.setNumber(tree.getNumber());
                    if (tree.getChildren() == null) {
                        List<TreeNode> myChildrens = new ArrayList<>();
                        myChildrens.add(t2);
                        tree.setChildren(myChildrens);
                    } else {
                        tree.getChildren().add(t2);
                    }
                }
            }
            if (idMap != null) {
                if (idMap.containsKey(tree.getCheckboxValue().toString())) {
                    tree.setChecked(true);
                }
            }
        }
        Gson gson = new Gson();
        List<String> jsonList = new ArrayList<>();
        for (TreeNode treeNode : newList) {
            jsonList.add(gson.toJson(treeNode));
        }
        return jsonList;
    }

    private List<TreeNode> rightsToTreeNodes(List<Rights> rightsList) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (Rights rights : rightsList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setAlias(rights.getFatherId().toString());
            treeNode.setCheckboxValue(rights.getId());
            treeNode.setName(rights.getRightName());
            treeNode.setFatherId(rights.getFatherId());
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }


    /**
     * 增加角色
     *
     * @param role 角色信息
     * @throws WarnException
     */
    @Override
    public void addRole(Role role) throws WarnException {

        if (role == null) {
            throw new WarnException("数据不能为空");
        }
        if (StringUtil.isEmpty(role.getName())) {
            throw new WarnException("角色名不能为空");
        }
        if(StringUtil.isEmpty(role.getRightIds())){
            throw new WarnException("请为该角色至少分配一个权限");
        }

        int row = roleDao.insert(role);
        if (row == 0) {
            throw new ErrorException("新建角色出错", role);
        }
    }

    /**
     * 获得全部角色
     */
    @Override
    public List<Role> getAllRoles() {
        return roleDao.selectAll();
    }

    /**
     * 根据id获取角色信息
     *
     * @param id
     * @return
     */
    @Override
    public Role getRoleById(int id) {
        return roleDao.selectById(id);
    }

    /**
     * 修改角色
     *
     * @param role 角色id
     * @throws WarnException
     */
    @Override
    public void modifyRole(Role role) throws WarnException {

        if (role == null) {
            throw new WarnException("数据不能为空");
        }
        if (StringUtil.isEmpty(role.getName())) {
            throw new WarnException("角色名不能为空");
        }
        if(StringUtil.isEmpty(role.getRightIds())){
            throw new WarnException("请为该角色至少分配一个权限");
        }

        int row = roleDao.updateById(role);
        if (row == 0) {
            throw new ErrorException("修改角色出错", role);
        }
    }

}
