package com.example.examsystem.mapper;

import com.example.examsystem.pojo.QuestionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionTypeMapper {
    /**
     * 查询题目类型
     * @return
     */
    @Select("select * from t_questiontype")
    List<QuestionType> selectType();
}
