package com.example.examsystem.util;

import org.springframework.web.bind.annotation.ControllerAdvice;

//自定义异常类
public class MyException extends RuntimeException{
    private MyExceptionCode code;

    public MyException(MyExceptionCode code){
        super(code.getDesc());
        this.code = code;
    }

    public MyExceptionCode getCode() {
        return code;
    }

    public void setCode(MyExceptionCode code) {
        this.code = code;
    }
}
