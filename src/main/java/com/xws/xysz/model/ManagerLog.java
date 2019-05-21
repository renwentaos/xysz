package com.xws.xysz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Table: manager_log
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerLog {
    private Integer id;

    /**
     * 管理员id
     */
    private Integer managerId;

    /**
     * 管理员名称
     */
    private String managerName;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 执行内容
     */
    private String operation;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 传入的参数及其注释
     */
    private String params;
}