package com.example.examsystem.service;

import com.example.examsystem.pojo.Scoredetail;
import com.example.examsystem.resp.ReadPaperResp;
import com.example.examsystem.resp.StuScoreResp;

import java.util.List;

public interface ScoredetailService {
    void savePaperTestRecord(List<Scoredetail> scoredetails);

    List<Scoredetail> selectPaperTestRecode(int stuId, int paperId);

    List<StuScoreResp> queryScore(int stuId);

    List<ReadPaperResp> selectReadPaper();

    void saveScore(int id ,int correntScore);
}
