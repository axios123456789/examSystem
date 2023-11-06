package com.example.examsystem.service;

import com.example.examsystem.req.PaperQuery;
import com.example.examsystem.req.PaperQuestionReq;
import com.github.pagehelper.PageInfo;

import java.awt.print.Paper;
import java.util.List;

public interface PaperService {
    PageInfo<Paper> selectPaper(PaperQuery paperQuery);

    void savePaper(com.example.examsystem.pojo.Paper paper);

    void delPaper(int id);

    void zujuan(PaperQuestionReq paperQuestionReq);

    List<Integer> selectPaperQuestion(int paperId);

    com.example.examsystem.pojo.Paper paperView(int paperId);
}
