package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.QuestionMapper;
import com.example.examsystem.pojo.Question;
import com.example.examsystem.pojo.QuestionxztOptions;
import com.example.examsystem.req.QuestionQuery;
import com.example.examsystem.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public PageInfo<Question> selectQuestion(QuestionQuery questionQuery) {
        //开启分页
        PageHelper.startPage(questionQuery.getPageNum(),questionQuery.getPageSize());
        List<Question> questions = questionMapper.selectQuestion(questionQuery);

        //创建分页信息对象
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        return pageInfo;
    }

    @Override
    public void saveQuestion(Question question) {
        if (question.getId() != 0){
            questionMapper.updateQuestion(question);

            System.out.println(question.getQuestionxztOptions());

            //判断题目如果是选择题 就要将选择题选项修改
            if (question.getQ_typeid() == 1){
                QuestionxztOptions questionxztOptions = question.getQuestionxztOptions();
                questionxztOptions.setQuestionId(question.getId());
                questionMapper.updateXztOptions(questionxztOptions);
            }
        }else {
            questionMapper.addQuestion(question);

            System.out.println(question.getQuestionxztOptions());

            //判断题目如果是选择题 就要将选择题选项添加到选项表中
            if (question.getQ_typeid() == 1){
                QuestionxztOptions questionxztOptions = question.getQuestionxztOptions();
                questionxztOptions.setQuestionId(question.getId());
                questionMapper.addXztOptions(questionxztOptions);
            }
        }
    }

    @Override
    public void delQuestion(int id) {
        questionMapper.delQuestion(id);
    }

    @Override
    public QuestionxztOptions selectXztOptions(int questionId) {
        return questionMapper.selectXztOptions(questionId);
    }
}
