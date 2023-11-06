package com.example.examsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@ToString
@Data
public class Question {
    private int id;

    @NotNull(message = "【题目】不能为空")
    private String questionTitle;   //问题题目

    private String questionAnswer;  //问题答案
    private Integer q_typeid;   //问题类型 1.选择 2.填空 3.判断 4.简答
    private int status; //问题状态 0.正常 1.停用

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @NotNull(message = "【分数】不能为空")
    private Integer grade;  //分数

    //选择题选项对象
    QuestionxztOptions questionxztOptions;
}
