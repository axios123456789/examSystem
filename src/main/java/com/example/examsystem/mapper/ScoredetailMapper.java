package com.example.examsystem.mapper;

import com.example.examsystem.pojo.Scoredetail;
import com.example.examsystem.resp.ReadPaperResp;
import com.example.examsystem.resp.StuScoreResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScoredetailMapper {
    /**
     * 保存学生考试的每一个题目信息 题目 分数 学生答案 学生分数....
     * @param scoredetails
     */
    void savePaperTestRecord(List<Scoredetail> scoredetails);

    /**
     * 查询某个学生是否考过某张试卷
     * @param stuId
     * @param paperId
     * @return
     */
    @Select("select * from t_scoredetail where stuId=#{stuId} and paperId=#{paperId}")
    List<Scoredetail> selectPaperTestRecode(int stuId, int paperId);

    /**
     * 查询学生成绩
     * @param stuId
     * @return
     */
    List<StuScoreResp> queryScore(int stuId);

    /**
     * 老师阅卷时查询需要显示的信息
     * @return
     */
    List<ReadPaperResp> selectReadPaper();

    /**
     * 保存老师修改的分数
     * @param correntScore
     */
    @Update("update t_scoredetail set correntScore=#{correntScore} where id = #{id}")
    void saveScore(int id, int correntScore);
}
