package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.LevelMapper;
import com.example.examsystem.pojo.Level;
import com.example.examsystem.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    @Autowired
    LevelMapper levelMapper;

    @Override
    public List<Level> selectLevel() {
        return levelMapper.selectLevel();
    }
}
