package com.xws.xysz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Table: rights
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rights {
    /**
     * id
     */
    private Integer id;

    /**
     * 权限父级id,0为最顶级
     */
    private Integer fatherId;

    /**
     * 权限名称
     */
    private String rightName;

    /**
     * 权限内容(url)
     */
    private String right;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 是否是页面,1是，0否
     */
    private Boolean isView;

    private Integer order;

    //--------------------特殊查询使用的字段-----------------------------
    //进入的页面是否为此权限或者此权限的子级
    private Boolean isEnter =false;

    private String url;//该权限的默认页面地址

    //子级权限
    private List<Rights> children ;
}