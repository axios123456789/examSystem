package com.example.examsystem.mapper;

import com.example.examsystem.pojo.Level;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LevelMapper {
    /**
     * 查询试卷等级
     * @return
     */
    @Select("select * from t_level")
    List<Level> selectLevel();
}
