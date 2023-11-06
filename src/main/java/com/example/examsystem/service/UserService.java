package com.example.examsystem.service;

import com.example.examsystem.pojo.Student;
import com.example.examsystem.pojo.User;
import com.example.examsystem.req.UserQuery;
import com.example.examsystem.resp.UserResp;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    //public List<User> selectUser();
    PageInfo<User> selectUser(UserQuery userQuery);

    void addUser(User user);

    void delUser(int id);

    List<UserResp> login(String username, String password);
}
