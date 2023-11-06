package com.example.examsystem.controller;

import com.example.examsystem.pojo.QuestionType;
import com.example.examsystem.service.QuestionTypeService;
import com.example.examsystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionTypeController {
    @Autowired
    QuestionTypeService questionTypeService;

    @RequestMapping("/selectType")
    public Result selectType(){
        List<QuestionType> questionTypes = questionTypeService.selectType();
        return Result.success(questionTypes);
    }
}
