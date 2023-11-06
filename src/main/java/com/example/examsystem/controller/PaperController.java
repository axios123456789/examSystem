package com.example.examsystem.controller;

import com.example.examsystem.req.PaperQuery;
import com.example.examsystem.req.PaperQuestionReq;
import com.example.examsystem.service.PaperService;
import com.example.examsystem.util.Result;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Paper;
import java.util.List;

@RestController
public class PaperController {
    @Autowired
    PaperService paperService;

    //查询试卷
    @RequestMapping("/selectPaper")
    public Result selectPaper(PaperQuery paperQuery){
        PageInfo<Paper> pageInfo = paperService.selectPaper(paperQuery);
        return Result.success(pageInfo);
    }

    //添加或修改试卷
    @PostMapping("/savePaper")
    public Result savePaper(@RequestBody @Valid com.example.examsystem.pojo.Paper paper){
        try {
            paperService.savePaper(paper);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //删除试卷
    @PostMapping("/delPaper")
    public Result delPaper(int id){
        try {
            paperService.delPaper(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //组卷时将选中的题目id即试卷id添加到t_paper_question表中
    @PostMapping("/zujuan")
    public Result zujuan(@RequestBody PaperQuestionReq paperQuestionReq){
        try {
            paperService.zujuan(paperQuestionReq);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //根据试卷id查询题目id
    @RequestMapping("/selectPaperQuestion/{paperId}")
    public Result selectPaperQuestion(@PathVariable int paperId){
        try {
            List<Integer> questionId =  paperService.selectPaperQuestion(paperId);
            return Result.success(questionId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //预览试卷时 根据试卷id查询试卷 题目 选择题选项
    @RequestMapping("paperView/{paperId}")
    public Result selectPaperView(@PathVariable int paperId){
        try {
            com.example.examsystem.pojo.Paper paper = paperService.paperView(paperId);
            return Result.success(paper);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }
}
