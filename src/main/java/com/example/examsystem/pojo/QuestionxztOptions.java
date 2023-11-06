package com.example.examsystem.pojo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class QuestionxztOptions {
    private int id;

    /**
     * 选择题选项A B C D
     */
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    //问题编号
    private int questionId;
}
