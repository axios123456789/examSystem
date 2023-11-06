package com.example.examsystem.req;

import lombok.Data;
import lombok.ToString;

import java.util.List;

//试卷组卷时后台接收的类
@ToString
@Data
public class PaperQuestionReq {
    private int paperId;     //试卷id
    private List<Integer> questionId;       //试卷题目id
}
