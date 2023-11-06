package com.example.examsystem.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Scoredetail {
    private int id;     //记录id
    private Integer stuId;      //学生id
    private Integer paperId;    //试卷id
    private Integer questionId; //问题id
    private String questionTitle; //问题题目
    private Integer q_typeid;   //问题类型
    private String questionAnswer; //答案
    private String questionScore;  //问题分数
    private String correntAnswer;  //学生答案
    private String correntScore;   //学生分数
}
