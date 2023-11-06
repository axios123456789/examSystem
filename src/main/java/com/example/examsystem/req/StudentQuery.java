package com.example.examsystem.req;

import lombok.Data;

//学生查询类
@Data
public class StudentQuery extends PageQuery{
    private String username;
}
