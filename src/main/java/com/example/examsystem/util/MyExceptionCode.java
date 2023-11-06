package com.example.examsystem.util;

//枚举类 定义异常名称 用户名或密码错误之类的
public enum MyExceptionCode {
    USERLOGINERROR("用户名或密码错误")
    ;

    private String desc;

    MyExceptionCode(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
