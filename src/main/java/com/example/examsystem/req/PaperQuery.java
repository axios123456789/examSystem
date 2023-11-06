package com.example.examsystem.req;

import lombok.Data;

//试卷查询类
@Data
public class PaperQuery extends PageQuery{
    private int levelid;
    private String name;
}
