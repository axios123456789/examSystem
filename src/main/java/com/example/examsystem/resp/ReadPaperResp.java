package com.example.examsystem.resp;

import lombok.Data;
import lombok.ToString;

//老师阅卷时返回给前端的阅卷信息
@Data
@ToString
public class ReadPaperResp {
    private int id;
    private String username;    //学生姓名
    private String name;    //试卷名字
    private String questionTitle;   //试卷问题
    private String questionAnswer;  //正确答案
    private int questionScore;  //题目分数
    private String correntAnswer;   //学生答案
    private String correntScore;    //学生分数
}
