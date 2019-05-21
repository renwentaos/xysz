package com.xws.xysz.model;

import com.xws.xysz.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Table: manager
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 扰码
     */
    private String pKey;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 状态，-1冻结/删除，0正常
     */
    private Integer state;

    /**
     * 拥有的角色，多个逗号隔开
     */
    private String roles;

    //--------------------特殊查询使用的字段-----------------------------
    private Map<String, String> rolesIds = new HashMap<String, String>();

    private List<Role> roleList = new ArrayList();
    private List<Rights> rightsList=new ArrayList();
    private List<Rights> rightsTree = new ArrayList<>();

    /**
     * 设置 拥有的角色，多个逗号隔开 字段
     */
    public void setRoles(String roles) {
        this.rolesIds.clear();

        if(!StringUtil.isEmpty(roles)) {
            String[] temp = roles.split(",");

            for (String s : temp) {
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                this.rolesIds.put(s, s);
            }

            this.roles = roles;
        }
    }

    /**
     * 根据当前实体内的rightsList生成权限树
     */
    public void createTreeList(){
        for (Rights rights : this.rightsList) {
            if(rights.getFatherId() == 0){
                this.rightsTree.add(rights);
            }
            for (Rights rights1 : this.rightsList) {
                if(rights1.getFatherId() == rights.getId()){
                    if (rights.getChildren() == null) {
                        List<Rights> children = new ArrayList<>();
                        children.add(rights1);
                        rights.setChildren(children);
                    }else{
                        rights.getChildren().add(rights1);
                    }
                }
            }
            if(!StringUtil.isEmpty(rights.getRight())&&rights.getIsView()) {
                String url = rights.getRight().replace("/\\d+-\\d+", "/1-10");
                if(url.indexOf("repayment/1-10")>-1){
                   url =  url.replace("1-10","1-12");
                }
                url = url.replace("*","");
                url = url.split("\\|")[0];
                rights.setUrl(url);
            }else{
                rights.setUrl(rights.getRight());
            }
        }
        treeUrlBuild(this.rightsTree);
    }

    //给没有设置url的上级菜单设置默认的url
    private String treeUrlBuild(List<Rights> rightsList){
        String result = null;
        for (Rights rights : rightsList) {
            String temp = null;
            if(StringUtil.isEmpty(rights.getUrl())){
                if(rights.getChildren() != null) {
                    temp = treeUrlBuild(rights.getChildren());
                    rights.setUrl(temp);
                }
            }else{
                temp = rights.getUrl();
            }
            if (result == null&&temp!=null) {
                result = temp;
            }
        }
        return result;
    }

    /**
     * 获得管理员权限默认页面
     */
    public String getDefaultPage(){
        String defaultPageUrl = "";
        if (rightsTree.size() > 0) {
            defaultPageUrl = rightsTree.get(0).getUrl();
        }
        return defaultPageUrl;
    }


}