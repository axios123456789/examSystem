package com.example.examsystem.resp;

import lombok.Data;
import lombok.ToString;

//返回给前端的学生登录用户对象
@Data
@ToString
public class StuResp {
    private int id;
    private String username;
    private String tel;
    private String email;
    private String stuNum;
    private String createTime;
    private String nickName;

    //token
    private String token;
}
