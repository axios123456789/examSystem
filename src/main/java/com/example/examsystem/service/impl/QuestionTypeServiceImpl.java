package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.QuestionTypeMapper;
import com.example.examsystem.pojo.QuestionType;
import com.example.examsystem.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {
    @Autowired
    QuestionTypeMapper questionTypeMapper;

    @Override
    public List<QuestionType> selectType() {
        return questionTypeMapper.selectType();
    }
}
