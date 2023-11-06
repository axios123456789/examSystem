package com.example.examsystem.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

//老师和管理员
@ToString
@Data
public class User {
    private int id;

    @NotNull(message = "【用户名】不能为空")
    private String username;

    @NotNull(message = "【密码】不能为空")
    private String password;

    @NotNull(message = "【电子邮箱】不能为空")
    private String email;

    @NotNull(message = "【电话号码】不能为空")
    private String tel;

    @NotNull(message = "【性别】不能为空")
    private String sex;
    private String headImg;
    private Date createTime;
    private int type;

}
