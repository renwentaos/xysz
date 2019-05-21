package com.xws.xysz.dao;


import com.xws.xysz.model.ManagerLog;

import java.util.List;

public interface ManagerLogDao {
    int insert(ManagerLog record);

    ManagerLog selectById(Integer id);

    List<ManagerLog> selectAll();

    int updateById(ManagerLog record);
}