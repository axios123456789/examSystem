package com.example.examsystem.mapper;

import com.example.examsystem.pojo.Question;
import com.example.examsystem.pojo.QuestionxztOptions;
import com.example.examsystem.req.QuestionQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 条件查询题库
     * @param questionQuery
     * @return
     */
    List<Question> selectQuestion(QuestionQuery questionQuery);

    /**
     * 添加题目到题库
     * @param question
     */
    @Insert("insert into t_questionbank(questionTitle, questionAnswer, q_typeid, status, createTime, grade) values (#{questionTitle}, #{questionAnswer}, #{q_typeid}, #{status}, now(), #{grade})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id") //回归主键
    void addQuestion(Question question);

    /**
     * 修改题目
     * @param question
     */
    @Update("update t_questionbank set questionTitle=#{questionTitle}, questionAnswer=#{questionAnswer}, q_typeid=#{q_typeid}, grade=#{grade} where id = #{id}")
    void updateQuestion(Question question);

    /**
     * 删除题目
     * @param id
     */
    @Delete("delete from t_questionbank where id=#{id}")
    void delQuestion(int id);

    /**
     * 根据id查询选项表中的选项
     * @param questionId
     * @return
     */
    @Select("select * from t_xzt_options where questionId=#{questionId}")
    QuestionxztOptions selectXztOptions(int questionId);

    /**
     * 添加选择题选项到选项表中
     * @param questionxztOptions
     */
    @Insert("insert into t_xzt_options(optionA,optionB,optionC,optionD,questionId) values (#{optionA},#{optionB},#{optionC},#{optionD},#{questionId})")
    void addXztOptions(QuestionxztOptions questionxztOptions);

    /**
     * 修改选择题选项
     * @param questionxztOptions
     */
    @Update("update t_xzt_options set optionA=#{optionA}, optionB=#{optionB}, optionC=#{optionC}, optionD=#{optionD} where questionId=#{questionId}")
    void updateXztOptions(QuestionxztOptions questionxztOptions);
}
