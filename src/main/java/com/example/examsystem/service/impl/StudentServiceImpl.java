package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.StudentMapper;
import com.example.examsystem.pojo.Student;
import com.example.examsystem.req.StudentQuery;
import com.example.examsystem.resp.StuResp;
import com.example.examsystem.service.StudentService;
import com.example.examsystem.util.MyException;
import com.example.examsystem.util.MyExceptionCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public PageInfo<Student> selectStu(StudentQuery studentQuery) {
        //开启分页
        PageHelper.startPage(studentQuery.getPageNum(),studentQuery.getPageSize());
        List<Student> students = studentMapper.selectStu(studentQuery);

        //创建分页信息对象
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return pageInfo;
    }

    @Override
    public void saveStu(Student student) {
        if (student.getId() != 0){//修改
           studentMapper.updateStu(student);
        }else {//添加
            studentMapper.addStu(student);
        }
    }

    @Override
    public void delStu(int id) {
        studentMapper.delStu(id);
    }

    @Override
    public List<StuResp> loginStu(String username, String password) {
        List<StuResp> stuLogin = studentMapper.loginStu(username, password);

        if (CollectionUtils.isEmpty(stuLogin)){
            throw new MyException(
                    MyExceptionCode.USERLOGINERROR
            );
        }else {
            return stuLogin;
        }
    }
}
