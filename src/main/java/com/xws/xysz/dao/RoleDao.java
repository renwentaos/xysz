package com.xws.xysz.dao;

import com.xws.xysz.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {
    int insert(Role record);

    Role selectById(Integer id);

    List<Role> selectAll();

    int updateById(Role record);
}