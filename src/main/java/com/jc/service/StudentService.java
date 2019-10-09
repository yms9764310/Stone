package com.jc.service;

import com.jc.model.Student;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 41
 *
 * @author 严脱兔
 */
public interface StudentService {
    List<Student> findAll(String page, String limit, String sname);
    int countGetAll();
    //删除
    int delStudent(int sid);
    //增加
    String addStudent(Student student);
    //修改
    String updateStudent(Student student);
    //根据ID查询
    Student findById(int sid);
}
