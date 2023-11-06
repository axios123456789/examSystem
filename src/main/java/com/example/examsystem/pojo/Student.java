package com.example.examsystem.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

//学生
@ToString
@Data
public class Student {
    private int id;

    @NotNull(message = "【学生姓名】不能为空")
    private String username;

    @NotNull(message = "【密码】不能为空")
    private String password;

    @NotNull(message = "【电话号码】不能为空")
    private String tel;

    @NotNull(message = "【电子邮箱】不能为空")
    private String email;

    @NotNull(message = "【学号】不能为空")
    private String stuNum;

    private Date createTime;
    private String nickName;    //昵称
}
