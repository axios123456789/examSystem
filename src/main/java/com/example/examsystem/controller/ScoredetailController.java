package com.example.examsystem.controller;

import com.example.examsystem.pojo.Scoredetail;
import com.example.examsystem.resp.ReadPaperResp;
import com.example.examsystem.resp.StuScoreResp;
import com.example.examsystem.service.ScoredetailService;
import com.example.examsystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScoredetailController {
    @Autowired
    ScoredetailService scoredetailService;

    /**
     * 保存学生答题信息
     * @param scoredetails
     * @return
     */
    @PostMapping("/savePaperTestRecord")
    public Result savePaperTestRecord(@RequestBody List<Scoredetail> scoredetails){
        try {
            scoredetailService.savePaperTestRecord(scoredetails);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    /**
     * 查询答题信息
     * @param stuId
     * @param paperId
     * @return
     */
    @RequestMapping("/selectPaperTestRecode")
    public Result selectPaperTestRecode(int stuId, int paperId){
        List<Scoredetail> scoredetails = scoredetailService.selectPaperTestRecode(stuId, paperId);
        return Result.success(scoredetails);
    }

    /**
     * 查询学生分数
     * @param stuId
     * @return
     */
    @RequestMapping("/queryScore/{stuId}")
    public Result queryScore(@PathVariable int stuId){
        List<StuScoreResp> stuScore = scoredetailService.queryScore(stuId);
        return Result.success(stuScore);
    }

    /**
     * 查询阅卷信息
     * @return
     */
    @RequestMapping("/selectReadPaper")
    public Result selectReadPaper(){
        List<ReadPaperResp> readPaperResps = scoredetailService.selectReadPaper();
        return Result.success(readPaperResps);
    }

    @PostMapping("/saveScore")
    public Result saveScore(int id, int correntScore){
        try {
            System.out.println("id"+id+"correntScore"+correntScore);
            scoredetailService.saveScore(id, correntScore);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }
}
