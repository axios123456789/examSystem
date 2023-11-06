package com.example.examsystem.service;

import com.example.examsystem.pojo.Question;
import com.example.examsystem.pojo.QuestionxztOptions;
import com.example.examsystem.req.QuestionQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QuestionService {
    PageInfo<Question> selectQuestion(QuestionQuery questionQuery);

    void saveQuestion(Question question);

    void delQuestion(int id);

    QuestionxztOptions selectXztOptions(int questionId);
}
