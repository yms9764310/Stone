package com.jc.mapper;

import com.jc.model.YzjSysDepartmentTest;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Repository
public interface YzjSysDepartmentTestMapper {
    List<YzjSysDepartmentTest> listDepartmet(@Param("start") Integer start,
                                             @Param("end") Integer end,
                                             @Param("name") String name);
    /**
     * 获取菜单大小
     * */
    int countDepartmet();
    //删除
    int deleteDepartment(int id);
    //修改
    Integer updateDepartment(YzjSysDepartmentTest sysDepartmentTest);
    //根据ID查询
    YzjSysDepartmentTest loadDepartmentById(int id);


    //查询表中是否有相同的上级部门和下级部门
    YzjSysDepartmentTest listDepartmentParent_Id(int parent_id);

    //树形结构
    List<YzjSysDepartmentTest> listDepartmentParentId();

    List<YzjSysDepartmentTest> childrenDepartmentName(int parent_id);

    List<YzjSysDepartmentTest> listDepartmentTestName(String name);

    YzjSysDepartmentTest getById(Integer id);


    YzjSysDepartmentTest listDepartmentTestByNameAndId(@Param("name")String name,@Param("id")int id);
    //判断上级下面是否有重复下级
    YzjSysDepartmentTest listDepartmentTestByNameAndParent_id(@Param("name")String name,@Param("parent_id")int parent_id);
    //单独创建部门信息
    Integer saveDepartmentTest(YzjSysDepartmentTest sysDepartmentTest);
    //批量导入
    String listDepartmentExcle(HttpServletRequest request, HttpServletResponse response);
    //获取下拉框数据
    List<YzjSysDepartmentTest> listDepartmetName();
    //获取编辑下拉框数据

    List<YzjSysDepartmentTest> listDepartmetById(int id);



}
