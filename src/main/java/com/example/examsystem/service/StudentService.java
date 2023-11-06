package com.example.examsystem.service;

import com.example.examsystem.pojo.Student;
import com.example.examsystem.req.StudentQuery;
import com.example.examsystem.resp.StuResp;
import com.example.examsystem.util.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface StudentService {
    PageInfo<Student> selectStu(StudentQuery studentQuery);

    void saveStu(Student student);

    void delStu(int id);

    List<StuResp> loginStu(String username, String password);
}
