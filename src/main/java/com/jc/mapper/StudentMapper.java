package com.jc.mapper;

import com.jc.model.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 40
 *
 * @author 严脱兔
 */
@Repository
public interface StudentMapper {
    //分页加模糊搜索
    List<Student> findAll(@Param("start") Integer start,
                          @Param("end") Integer end,
                          @Param("sname") String sname);
    /**
     * 获取菜单大小
     * */
    int countGetAll();
    //删除
    void delStudent(int sid);
    //增加
    void addStudent(Student student);
    //修改
    void updateStudent(Student student);
    //根据ID查询
    Student findById(int sid);

}
