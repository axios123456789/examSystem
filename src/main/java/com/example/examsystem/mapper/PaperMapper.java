package com.example.examsystem.mapper;

import com.example.examsystem.pojo.Paper;
import com.example.examsystem.req.PaperQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaperMapper {
    /**
     * 条件查询试卷
     * @param paperQuery
     * @return
     */
    List<Paper> selectPaper(PaperQuery paperQuery);

    /**
     * 添加试卷
     * @param paper
     */
    @Insert("insert into t_paper(`name`, startTime, endTime, createTime, levelid) values (#{name}, #{startTime}, #{endTime}, now(), #{levelid})")
    void addPaper(Paper paper);

    /**
     * 修改试卷
     * @param paper
     */
    @Update("update t_paper set `name`=#{name}, startTime=#{startTime}, endTime=#{endTime}, levelid=#{levelid} where id=#{id}")
    void updatePaper(Paper paper);

    /**
     * 删除试卷
     * @param id
     */
    @Delete("delete from t_paper where id=#{id}")
    void delPaper(int id);

    /**
     * 将组卷的试卷id和题目id添加到试卷问题表中
     * @param collect
     */
    void zujuan(List<Map> collect);

    /**
     * 根据试卷的id删除试卷问题表中的相关数据
     * @param paperId
     */
    @Delete("delete from t_paper_question where paperId=#{paperId}")
    void delQuestion(int paperId);

    /**
     * 根据试卷id查询题目id
     * @param paperId
     * @return
     */
    @Select(" select questionId from t_paper_question where paperId=#{paperId};")
    List<Integer> selectPaperQuestion(int paperId);

    Paper paperView(int paperId);
}
