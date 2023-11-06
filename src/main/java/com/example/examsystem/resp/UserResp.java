package com.example.examsystem.resp;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

//返回给前端的登录用户对象
@Data
@ToString
public class UserResp {
    private int id;
    private String username;
    private String email;
    private String tel;
    private String sex;
    private String headImg;
    private Date createTime;
    private int type;

    //token
    private String toKen;
}
