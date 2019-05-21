package com.xws.xysz.model;

import com.xws.xysz.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Table: role
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限，多个逗号隔开
     */
    private String rightIds;

    /**
     * 设置 角色权限，多个逗号隔开 字段
     */
    public void setRightIds(String rightIds) {

        this.rights.clear();

        if (!StringUtil.isEmpty(rightIds)) {
            String[] temp = rightIds.split(",");

            for (String s : temp) {
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                this.rights.put(s, s);
            }

        }
        this.rightIds = rightIds;
    }

    //--------------------特殊查询使用的字段-----------------------------
    //角色拥有的权限列表`
    private Map<String, String> rights = new HashMap<>();
}