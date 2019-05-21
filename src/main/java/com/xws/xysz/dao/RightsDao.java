package com.xws.xysz.dao;

import com.xws.xysz.model.Rights;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RightsDao {
    int insert(Rights record);

    Rights selectById(Integer id);

    List<Rights> selectAll();

    int updateById(Rights record);
}