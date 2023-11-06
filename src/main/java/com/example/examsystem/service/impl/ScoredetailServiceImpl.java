package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.ScoredetailMapper;
import com.example.examsystem.pojo.Scoredetail;
import com.example.examsystem.resp.ReadPaperResp;
import com.example.examsystem.resp.StuScoreResp;
import com.example.examsystem.service.ScoredetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoredetailServiceImpl implements ScoredetailService {
    @Autowired
    ScoredetailMapper scoredetailMapper;

    @Override
    public void savePaperTestRecord(List<Scoredetail> scoredetails) {
        scoredetailMapper.savePaperTestRecord(scoredetails);
    }

    @Override
    public List<Scoredetail> selectPaperTestRecode(int stuId, int paperId) {
        List<Scoredetail> scoredetails = scoredetailMapper.selectPaperTestRecode(stuId, paperId);
        System.out.println("问题信息"+scoredetails);
        return scoredetails;
    }

    @Override
    public List<StuScoreResp> queryScore(int stuId) {
        List<StuScoreResp> stuScore = scoredetailMapper.queryScore(stuId);
        return stuScore;
    }

    @Override
    public List<ReadPaperResp> selectReadPaper() {
        List<ReadPaperResp> readPaperResps = scoredetailMapper.selectReadPaper();
        return readPaperResps;
    }

    @Override
    public void saveScore(int id ,int correntScore) {
        scoredetailMapper.saveScore(id ,correntScore);
    }
}
