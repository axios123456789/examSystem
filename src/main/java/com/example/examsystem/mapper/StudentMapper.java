package com.example.examsystem.mapper;

import com.example.examsystem.pojo.Student;
import com.example.examsystem.req.StudentQuery;
import com.example.examsystem.resp.StuResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 条件查询学生
     * @param studentQuery 查询学生的条件类
     * @return
     */
    List<Student> selectStu(StudentQuery studentQuery);

    /**
     * 添加学生
     * @param student
     */
    @Insert("insert into t_student(username,password,tel,email,stuNum,createTime,nickName) values (#{username},#{password},#{tel},#{email},#{stuNum},now(),#{nickName}) ")
    void addStu(Student student);

    /**
     * 修改学生
     * @param student
     */
    @Update("update t_student set username=#{username}, password=#{password}, tel=#{tel}, email=#{email}, stuNum=#{stuNum}, nickName=#{nickName} where id = #{id}")
    void updateStu(Student student);

    @Delete("delete from t_student where id = #{id}")
    void delStu(int id);

    /**
     * 登录是否成功的校验查询
     * @param username
     * @param password
     * @return
     */
    @Select("select * from t_student where username=#{username} and password=#{password}")
    List<StuResp> loginStu(String username, String password);
}
