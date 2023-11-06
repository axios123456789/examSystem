package com.example.examsystem.req;

import lombok.Data;

//用户查询类
@Data
public class UserQuery extends PageQuery{
    private int type;
    private String username;
}
