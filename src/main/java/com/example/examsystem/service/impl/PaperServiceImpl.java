package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.PaperMapper;
import com.example.examsystem.req.PaperQuery;
import com.example.examsystem.req.PaperQuestionReq;
import com.example.examsystem.service.PaperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Paper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

    @Override
    public PageInfo<Paper> selectPaper(PaperQuery paperQuery) {
        //开启分页
        PageHelper.startPage(paperQuery.getPageNum(),paperQuery.getPageSize());
        List<com.example.examsystem.pojo.Paper> papers = paperMapper.selectPaper(paperQuery);

        //创建分页信息对象
        PageInfo<Paper> pageInfo = new PageInfo(papers);
        return pageInfo;
    }

    @Override
    public void savePaper(com.example.examsystem.pojo.Paper paper) {
        if (paper.getId() != 0){//修改
            paperMapper.updatePaper(paper);
        }else {//添加
            paperMapper.addPaper(paper);
        }
    }

    @Override
    public void delPaper(int id) {
        paperMapper.delPaper(id);
    }

    @Override
    public void zujuan(PaperQuestionReq paperQuestionReq) {
        //添加之前先删除试卷的题目
        paperMapper.delQuestion(paperQuestionReq.getPaperId());

        if (paperQuestionReq.getQuestionId() != null && paperQuestionReq.getQuestionId().size() != 0){
            //添加试卷id与题库的题目id到试卷与题库的关联表中
            /**
            将paperQuestionReq转化为 list<Map>结构
            其原因是：paperQuestionReq.getQuestionId()是List<Integer>类型，其中有多个值
             若直接将paperQuestionReq传给paperMapper执行sql添加语句，则最终添加到的题目id数据只有
             paperQuestionReq.getQuestionId()中的第一条，而这样的化就需要多次添加了
             而转化为List<Map>结构就解决了需要多次添加的问题，
             其可以将数据变为[{paperId=1,questionId=1},{paperId=1,questionId=2}....]返回给paperMapper执行添加的
             sql语句一次性添加完
             */
            List<Map> collect = paperQuestionReq.getQuestionId().stream().map(qid -> {
                Map mp = new HashMap();
                mp.put("paperId", paperQuestionReq.getPaperId());
                mp.put("questionId", qid);
                return mp;
            }).collect(Collectors.toList());

            paperMapper.zujuan(collect);
        }
    }

    //从关联表中根据试卷id查询题目id
    @Override
    public List<Integer> selectPaperQuestion(int paperId) {
        return paperMapper.selectPaperQuestion(paperId);
    }

    //查询所有问题 选项
    @Override
    public com.example.examsystem.pojo.Paper paperView(int paperId) {
        return paperMapper.paperView(paperId);
    }
}
