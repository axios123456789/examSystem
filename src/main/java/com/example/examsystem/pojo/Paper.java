package com.example.examsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//试卷
@ToString
@Data
public class Paper {
    private int id;

    @NotNull(message = "【试卷名】不能为空")
    private String name;

    @NotNull(message = "【试卷难度】不能为空")
    private Integer levelid;    //试卷水平

    //@NotNull(message = "【开始时间】不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //前端传给后端时后端转化的时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    //转化为json时返回的时间格式
    private Date startTime; //开始时间

    //@NotNull(message = "【结束时间】不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //前端传给后端时后端转化的时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    //转化为json时返回的时间格式
    private Date endTime;   //结束时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    //转化为json时返回的时间格式
    private Date createTime;

    private List<Question> questionList = new ArrayList<>();
}
