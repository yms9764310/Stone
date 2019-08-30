package com.jc.service;

import com.jc.model.OrgNodeResponse;
import com.jc.model.YzjSysDepartmentTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface YzjSysDepartmentTestService {
    //分页查询
    List<YzjSysDepartmentTest> listDepartmet(String page, String limit, String name);
    //统计个数
    int countDepartmet();
    //删除
    int deleteDepartment(int id);
    //修改
    Integer updateDepartment(YzjSysDepartmentTest sysDepartmentTest);
    //根据ID查询
    YzjSysDepartmentTest loadDepartmentById(int id);
    //批量导入
    String listDepartmentExcle(HttpServletRequest request, HttpServletResponse response);
    //单独创建部门信息
    Integer  saveDepartmentTest(YzjSysDepartmentTest sysDepartmentTest);

    //获取下拉框数据
    List<YzjSysDepartmentTest> listDepartmetName();

    //添加查询表中是否有相同的上级部门和下级部门
    YzjSysDepartmentTest listDepartmentParent_Id(int parent_id);


    List<YzjSysDepartmentTest> listDepartmentTestName(String name);
    //修改的判断是否有相同下级部门
    YzjSysDepartmentTest listDepartmentTestByNameAndId(String name,int id);
    //查询更新下拉框数据
    List<YzjSysDepartmentTest> listDepartmetById(int id);

    List<OrgNodeResponse> listDepartmentParentId();

    YzjSysDepartmentTest getById(Integer id);
}
