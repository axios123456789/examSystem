package com.example.examsystem.mapper;

import com.example.examsystem.pojo.User;
import com.example.examsystem.req.UserQuery;
import com.example.examsystem.resp.UserResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询老师列表
     * @return
     */
    /*@Select("select * from t_user")
    public List<User> selectUser();*/
    //@Select("select * from t_user where type = #{type}")
    List<User> selectUser(UserQuery userQuery);

    /**
     * 添加老师
     * @param user
     */
    @Insert("insert into t_user(username,password,email,tel,sex,headImg,createTime,`type`) values (#{username},#{password},#{email},#{tel},#{sex},#{headImg},now(),#{type})")
    public void addUser(User user);

    /**
     * 修改老师信息
     * @param user
     */
    @Update("update t_user set username=#{username}, password=#{password}, email=#{email}, tel=#{tel}, sex=#{sex}, headImg=#{headImg}, createTime=#{createTime}, `type`=#{type} where id=#{id}")
    public void updateUser(User user);

    /**
     * 根据id删除老师信息
     * @param id
     */
    @Delete("delete from t_user where id = #{id}")
    public void delUser(int id);

    /**
     * 查询登录是否成功
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Select("select * from t_user where username=#{username} and password=#{password}")
    List<UserResp> login(String username, String password);
}
