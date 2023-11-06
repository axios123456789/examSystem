package com.example.examsystem.service.impl;

import com.example.examsystem.mapper.UserMapper;
import com.example.examsystem.pojo.Student;
import com.example.examsystem.pojo.User;
import com.example.examsystem.req.PageQuery;
import com.example.examsystem.req.UserQuery;
import com.example.examsystem.resp.UserResp;
import com.example.examsystem.service.UserService;
import com.example.examsystem.util.MyException;
import com.example.examsystem.util.MyExceptionCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    /*public List<User> selectUser() {
       return userMapper.selectUser();
    }*/
    public PageInfo<User> selectUser(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
        List<User> users = userMapper.selectUser(userQuery);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public void addUser(User user) {
        if (user.getId()!=0){//修改
            userMapper.updateUser(user);
        }else {//添加
            userMapper.addUser(user);
        }
    }

    @Override
    public void delUser(int id) {
        userMapper.delUser(id);
    }

    @Override
    public List<UserResp> login(String username, String password) {
        List<UserResp> login = userMapper.login(username, password);
        System.out.println("查询到的登录信息"+login);

        //判断login是否为空
        if (CollectionUtils.isEmpty(login)){
            throw new MyException(
                    MyExceptionCode.USERLOGINERROR
            );
        }else {
            return login;
        }
    }

}
