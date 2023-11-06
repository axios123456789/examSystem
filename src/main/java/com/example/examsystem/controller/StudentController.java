package com.example.examsystem.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.example.examsystem.pojo.Student;
import com.example.examsystem.req.StudentQuery;
import com.example.examsystem.resp.StuResp;
import com.example.examsystem.service.StudentService;
import com.example.examsystem.util.Result;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

//学生的增删改查控制类
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    RedisTemplate redisTemplate;

    //查询学生
    @RequestMapping("/selectStu")
    public Result selectStu(StudentQuery studentQuery){
        PageInfo<Student> pageInfo = studentService.selectStu(studentQuery);
        return Result.success(pageInfo);
    }

    //添加修改学生
    @PostMapping("/saveStu")
    public Result saveStu(@RequestBody @Valid Student student){
        try {
            studentService.saveStu(student);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //删除学生
    @PostMapping("/delStu")
    public Result delStu(int id){
        try {
            studentService.delStu(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //学生登录
    @RequestMapping("/loginStu")
    public Result loginStu(String username, String password){
        List<StuResp> stuLogin = studentService.loginStu(username,password);

        //生成token 保存token
        String token = IdUtil.randomUUID();
        stuLogin.get(0).setToken(token);

        //保存到redis中1小时
        redisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(stuLogin), 3600, TimeUnit.SECONDS);

        return Result.success(stuLogin);
    }

    //退出登录
    @RequestMapping("/stuLoginOut/{token}")
    public Result stuLoginOut(@PathVariable String token){
        redisTemplate.delete(token);
        return Result.success();
    }
}
