package com.xws.xysz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by 杨光 on 2017/7/8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private String name;
    private Boolean spread = true;
    private String alias;
    private Integer checkboxValue;
    private Integer fatherId;
    private Boolean checked = false;
    private Integer number;//当前节点属于第几个顶级节点,0开始

    //    checked: true,//复选框默认是否选中
    private List<TreeNode> children;


}
