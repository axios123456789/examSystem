package com.example.examsystem.controller;

import com.example.examsystem.pojo.Level;
import com.example.examsystem.service.LevelService;
import com.example.examsystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LevelController {
    @Autowired
    LevelService levelService;

    @RequestMapping("/selectLevel")
    public Result selectLevel(){
        List<Level> levels = levelService.selectLevel();
        return Result.success(levels);
    }
}
