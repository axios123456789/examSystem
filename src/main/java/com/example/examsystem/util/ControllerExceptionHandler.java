package com.example.examsystem.util;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常的统一处理
@ControllerAdvice
public class ControllerExceptionHandler {
    //出现 MethodArgumentNotValidException 错误时调用这个方法
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result validExceptionHandler(MethodArgumentNotValidException e){
        Result result = new Result();
        result.setCode("500");
        result.setMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result validExceptionHandler(MyException e){
        Result result = new Result();
        result.setCode("500");
        result.setMsg(e.getCode().getDesc());
        return result;
    }
}
