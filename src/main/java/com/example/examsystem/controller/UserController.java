package com.example.examsystem.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.example.examsystem.pojo.User;
import com.example.examsystem.req.UserQuery;
import com.example.examsystem.resp.UserResp;
import com.example.examsystem.service.UserService;
import com.example.examsystem.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

//老师和管理员的增删改查控制类
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;    //操作redis

    //查询老师
    @RequestMapping("/selectUser")
    /*public String selectUser(int pageNum, int pageSize){
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userService.selectUser();

        //创建分页信息对象
        PageInfo<User> pageInfo = new PageInfo<>(users);

        //将对象转化为json数据
        String s = JSONUtil.toJsonStr(pageInfo);
        System.out.println(users);
        return s;
    }*/
    public Result selectUser(UserQuery userQuery){
        PageInfo<User> pageInfo = userService.selectUser(userQuery);
        return Result.success(pageInfo);
    }

    //添加修改老师
    @PostMapping("/addUser")
    public Result addUser(@RequestBody @Valid User user){
        try {
            userService.addUser(user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //删除老师
    @PostMapping("/delUser")
    public Result delUser(int id){
        try {
            userService.delUser(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500","失败");
        }
    }

    //登录
    @RequestMapping("/login")
    public Result login(String username, String password){
        System.out.println("登录信息:"+username+"---"+password);
        List<UserResp> login = userService.login(username, password);
        //System.out.println("查询到的登录信息"+login);

        //生成token 保存token
        String token = IdUtil.randomUUID();     //唯一标识
        login.get(0).setToKen(token);

        //保存到redis中1小时
        redisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(login), 3600, TimeUnit.SECONDS);

        return Result.success(login);
    }

    //退出登录
    @RequestMapping("/loginOut/{token}")
    public Result loginOut(@PathVariable String token){
        redisTemplate.delete(token);
        return Result.success();
    }
}
