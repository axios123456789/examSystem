package com.example.examsystem.req;

import lombok.Data;

@Data
public class QuestionQuery extends PageQuery{
    private String questionTitle;
}
