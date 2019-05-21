package com.xws.xysz.dao;

import com.xws.xysz.model.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerDao {
    int insert(Manager record);

    Manager selectById(Integer id);

    List<Manager> selectAll(@Param("state") String state);

    int updateById(Manager record);


    /**
     * 根据id获得管理员和权限信息
     */
    Manager selectManagerAndRightsById(Integer id);



    /**
     * 根据用户名获取Manager信息
     * @param userName 用户名
     * @return manager信息
     */
    Manager selectByUsername(String userName);
}