package com.example.examsystem.resp;

import lombok.Data;
import lombok.ToString;

//显示成绩的类
@Data
@ToString
public class StuScoreResp {
    private int stuId;
    private String username;

    private int paperId;
    private String name;

    private int totalScore; //试卷分数
}
