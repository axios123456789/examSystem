package com.example.examsystem.controller;

import com.example.examsystem.pojo.Question;
import com.example.examsystem.pojo.QuestionxztOptions;
import com.example.examsystem.req.QuestionQuery;
import com.example.examsystem.service.QuestionService;
import com.example.examsystem.util.Result;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    /**
     * 查询题库
     * @param questionQuery 查询的条件
     * @return
     */
    @RequestMapping("/selectQuestion")
    public Result selectQuestion(QuestionQuery questionQuery){
        PageInfo<Question> pageInfo = questionService.selectQuestion(questionQuery);
        return Result.success(pageInfo);
    }

    /**
     * 添加修改题库
     * @param question 前端传回来的json数据转化成的Question对象
     * @return
     */
    @PostMapping("/saveQuestion")
    public Result saveQuestion(@RequestBody @Valid Question question){
        try {
            questionService.saveQuestion(question);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    /**
     * 删除题目
     * @param id 从前端传回来的要删除的题目的id
     * @return
     */
    @PostMapping("/delQuestion")
    public Result delQuestion(int id){
        try {
            questionService.delQuestion(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    @RequestMapping("/selectXztOptions/{questionId}")
    public Result selectXztOptions(@PathVariable int questionId){
        QuestionxztOptions questionxztOptions = questionService.selectXztOptions(questionId);
        return Result.success(questionxztOptions);
    }
}
