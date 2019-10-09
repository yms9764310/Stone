package com.jc.serviceImpl;

import com.jc.mapper.StudentMapper;
import com.jc.model.Student;
import com.jc.service.StudentService;
import com.jc.beans.response.PageRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;
    public List<Student> findAll(String page, String limit, String sname) {
        PageRange pageRange = new PageRange(page, limit);
        return studentMapper.findAll(pageRange.getStart(),pageRange.getEnd(),sname);
    }
    public int countGetAll() {
        return studentMapper.countGetAll();
    }

    @Override
    public int delStudent(int sid) {
        studentMapper.delStudent(sid);
        return sid;
    }

    @Override
    public String addStudent(Student student) {
        studentMapper.addStudent(student);
        return "success";
    }

    @Override
    public String updateStudent(Student student) {
        studentMapper.updateStudent(student);
        return "success";
    }

    @Override
    public Student findById(int sid) {
        return studentMapper.findById(sid);
    }
}
