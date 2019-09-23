package com.jc.serviceImpl;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageRange;
import com.jc.beans.response.ResultBean;
import com.jc.mapper.YzjSysDepartmentTestMapper;
import com.jc.mapper.YzjSysLoginUserTestMapper;
import com.jc.model.OrgNodeResponse;
import com.jc.model.YzjSysDepartmentTest;
import com.jc.model.YzjSysLoginUser;
import com.jc.service.YzjSysDepartmentTestService;
import com.jc.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class YzjSysDepartmentTestServiceImpl implements YzjSysDepartmentTestService {
    @Resource
    private YzjSysDepartmentTestMapper sysDepartmentTestMapper;
    @Resource
    private YzjSysLoginUserTestMapper yzjSysLoginUserTestMapper;

    @Override
    public List<YzjSysDepartmentTest> listDepartmet(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        List<YzjSysDepartmentTest> result = sysDepartmentTestMapper.listDepartmet(pageRange.getStart(),pageRange.getEnd(),name);
        return result;
    }

    @Override
    public int countDepartmet() {
        return sysDepartmentTestMapper.countDepartmet();
    }

    @Override
    public int deleteDepartment(int id) {

        return sysDepartmentTestMapper.deleteDepartment(id);
    }

    @Override
    public Integer updateDepartment(YzjSysDepartmentTest yzjSysDepartmentTest) {
        Date date = new Date();//获取时间
        //判断是否时管理员
        int id = 1;
        //获取登录人员id
        YzjSysLoginUser yzjSysLoginUser = yzjSysLoginUserTestMapper.loadLoginUserById(id);
        //获取前端添加的上级部门id,部门名称
        int parent_id = yzjSysDepartmentTest.getId();
        String name = yzjSysDepartmentTest.getName();
        //查询部门表里是否有部门名称
        int did = yzjSysDepartmentTest.getDid();
        Date create_date = yzjSysDepartmentTest.getCreate_date();

        yzjSysDepartmentTest.setId(did);
        yzjSysDepartmentTest.setCreator(id);
        yzjSysDepartmentTest.setCreate_date(yzjSysDepartmentTest.getCreate_date());
        yzjSysDepartmentTest.setParent_id(parent_id);
        yzjSysDepartmentTest.setModifier(id);
        yzjSysDepartmentTest.setModify_date(date);
        yzjSysDepartmentTest.setState("很奈斯");

        YzjSysDepartmentTest depaparentName = sysDepartmentTestMapper.listDepartmentTestByNameAndId(name,did);

        YzjSysDepartmentTest depaparentParent_idandName = sysDepartmentTestMapper.listDepartmentTestByNameAndParent_id(name,parent_id);

        //参加校验
        if(depaparentParent_idandName==null){//判断上级部门下是否有下级部门，不相同，
            //YzjSysDepartmentTest yzjSysDepartmentTest1 = new YzjSysDepartmentTest(yzjSysLoginUser.getCreator(),date,yzjSysLoginUser.getModifier(),date,"很好",parent_id,name);
            sysDepartmentTestMapper.saveDepartmentTest(yzjSysDepartmentTest);
            return 1;

        }
        else {
            return 0;
        }
    }

    @Override
    public YzjSysDepartmentTest loadDepartmentById(int id) {

        return sysDepartmentTestMapper.loadDepartmentById(id);
    }

    @Override
    public String listDepartmentExcle(HttpServletRequest request, HttpServletResponse response) {

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);//ajax提交，做文件上传时，由于上传的文件是另一台服务器
        //读取文件
        MultipartFile file = multipartRequest.getFile("upfile");
        //判断文件是否存在
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null; //读取数据
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建集合
        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

//该处可调用service相应方法进行数据保存到数据库中，现只对数据输出

            //获取excel的内容，放入集合内
            List<Object> lo = listob.get(0);
            //创建对象，将获取到的excle内容放入这个对象
            YzjSysDepartmentTest vo = new YzjSysDepartmentTest();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
            //获取登录id
            int id = 1;
            YzjSysLoginUser seleccid = yzjSysLoginUserTestMapper.loadLoginUserById(id);
            //判断是否有这个集合
            List<YzjSysDepartmentTest> yzjSysDepartmentTests = sysDepartmentTestMapper.listDepartmetName();
            if(yzjSysDepartmentTests.size()==0){

                //根据登录表中的人员名称，匹配excle内容，将对应的创建人存入对象
                vo.setCreator(seleccid.getCreator());

                vo.setCreate_date(new Date());
                //修改人
                vo.setModifier(seleccid.getModifier());

                vo.setModify_date(new Date());
                // 表格的第一列   注意数据格式需要对应实体类属性
                //状态
                vo.setState(String.valueOf(lo.get(0)));
                //上级部门

                vo.setParent_id(0);

                //部门名称
                vo.setName(String.valueOf(lo.get(1)));
                sysDepartmentTestMapper.saveDepartmentTest(vo);
            }
        yzjSysDepartmentTests.add(vo);



        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int j = 0; j < listob.size(); j++) {

            //获取excel的内容，放入集合内
            List<Object> lo1 = listob.get(j);
            //创建对象，将获取到的excle内容放入这个对象
            YzjSysDepartmentTest vo1 = new YzjSysDepartmentTest();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
            //获取登录id


                //根据登录表中的人员名称，匹配excle内容，将对应的创建人存入对象
            vo1.setCreator(seleccid.getCreator());

            vo1.setCreate_date(new Date());
                //修改人
            vo1.setModifier(seleccid.getModifier());

            vo1.setModify_date(new Date());
                // 表格的第一列   注意数据格式需要对应实体类属性
                //状态
            vo1.setState(String.valueOf(lo1.get(0)));
                //上级部门
                for (int g = 0; g < yzjSysDepartmentTests.size(); g++) {
                    if (String.valueOf(lo1.get(1)).equals(yzjSysDepartmentTests.get(g).getName())) {
                        vo1.setParent_id(yzjSysDepartmentTests.get(g).getId());
                    }
                }
                //部门名称
                vo1.setName(String.valueOf(lo1.get(2)));
                YzjSysDepartmentTest depaparentParent_idandName = sysDepartmentTestMapper.listDepartmentTestByNameAndParent_id(vo1.getName(), vo1.getParent_id());
                //判断是否有相同的部门名称存在
                if (depaparentParent_idandName == null) {
                    sysDepartmentTestMapper.saveDepartmentTest(vo1);
                    yzjSysDepartmentTests.add(vo1);
                } else {
                    return "false";
                }


            // vo.setCategory(String.valueOf(lo.get(6)));   // 表格的第七列
            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));

			/*if(j == null)
			{
		            userMapper.insert(vo);
			}
			else
			{
		            userMapper.updateByPrimaryKey(vo);
			}*/
        }
        return "success";
    }

    @Override
    public Integer saveDepartmentTest(YzjSysDepartmentTest yzjSysDepartmentTest) {
        Date date = new Date();//获取时间
        int id = 1;
        //获取登录人员id
        YzjSysLoginUser yzjSysLoginUser = yzjSysLoginUserTestMapper.loadLoginUserById(id);
        //获取前端添加的上级部门id,部门名称
        int parent_id = yzjSysDepartmentTest.getId();
        String name = yzjSysDepartmentTest.getName();
        //
        yzjSysDepartmentTest.setCreator(id);
        yzjSysDepartmentTest.setCreate_date(date);
        yzjSysDepartmentTest.setModifier(id);
        yzjSysDepartmentTest.setModify_date(date);
        yzjSysDepartmentTest.setParent_id(parent_id);
        yzjSysDepartmentTest.setState("很奈斯");

        //查询部门表里是否有部门名称和上级部门
        YzjSysDepartmentTest depaparentparent_id = sysDepartmentTestMapper.listDepartmentParent_Id(parent_id);

        List<YzjSysDepartmentTest> depaparentName = sysDepartmentTestMapper.listDepartmentTestName(name);



        YzjSysDepartmentTest depaparentParent_idandName = sysDepartmentTestMapper.listDepartmentTestByNameAndParent_id(name,parent_id);
        if(depaparentparent_id==null){//判断部门表是否有这个上级部门，不相同
            if(depaparentName==null){//判断是否有有下级部门,不相同
//                YzjSysDepartmentTest yzjSysDepartmentTest1 = new YzjSysDepartmentTest(yzjSysLoginUser.getCreator(),date,yzjSysLoginUser.getModifier(),date,"很好",parent_id,yzjSysDepartmentTest.getName());
             sysDepartmentTestMapper.saveDepartmentTest(yzjSysDepartmentTest);
             return 1;
            }
            else{
                return 0;
            }
        }
        else {
            if(depaparentParent_idandName==null){//判断上级部门下是否有下级部门，不相同，
                //YzjSysDepartmentTest yzjSysDepartmentTest1 = new YzjSysDepartmentTest(yzjSysLoginUser.getCreator(),date,yzjSysLoginUser.getModifier(),date,"很好",parent_id,name);
                sysDepartmentTestMapper.saveDepartmentTest(yzjSysDepartmentTest);
                return 1;

            }
            else {
                return 0;
            }
        }
    }

    @Override
    public List<YzjSysDepartmentTest> listDepartmetName() {
        return sysDepartmentTestMapper.listDepartmetName();
    }

    @Override
    public YzjSysDepartmentTest listDepartmentParent_Id(int parent_id) {
        return sysDepartmentTestMapper.listDepartmentParent_Id(parent_id);
    }

    @Override
    public List<YzjSysDepartmentTest> listDepartmentTestName(String name) {
        return sysDepartmentTestMapper.listDepartmentTestName(name);
    }


    @Override
    public YzjSysDepartmentTest listDepartmentTestByNameAndId(String name,int id) {
        return sysDepartmentTestMapper.listDepartmentTestByNameAndId(name,id);
    }


    @Override
    public List<YzjSysDepartmentTest> listDepartmetById(int id) {
        return sysDepartmentTestMapper.listDepartmetById(id);
    }

    @Override
    public List<OrgNodeResponse> listDepartmentParentId() {
        //先查询出所有的组织结构
        List<YzjSysDepartmentTest> parentIdList = sysDepartmentTestMapper.listDepartmentParentId();
        parentIdList = sysDepartmentTestMapper.listDepartmentParentId();

        //转换成树
        return convert2TreeNodes(parentIdList);

    }

    @Override
    public YzjSysDepartmentTest getById(Integer id) {
        return null;
    }

//******************************* 私有方法区*****************************************************************
    private List<OrgNodeResponse> convert2TreeNodes(List<YzjSysDepartmentTest> parentIdList) {
        List<OrgNodeResponse> res = new LinkedList<OrgNodeResponse>();// 对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据
        //获取所有的根结构
        Iterator<YzjSysDepartmentTest> it = parentIdList.iterator();//第一次调用Iterator的next()方法时，它返回序列的第一个元素。
        while (it.hasNext()) {
            YzjSysDepartmentTest d = it.next();
            if (StringUtils.isBlank(d.getParent_rew())) {//拿到根机构
                OrgNodeResponse node = new OrgNodeResponse();
                node.setId(d.getId()+"");
                node.setName(d.getName());
                node.setHref("#");
                node.setSpread(true);
                node.setChildren(getChildrenNodes(d.getId(), parentIdList));
                res.add(node);
            }
        }

        return res;
    }

    private List<OrgNodeResponse> getChildrenNodes(Integer parent_id, List<YzjSysDepartmentTest> parentIdList) {

       //获取子结构
        List<OrgNodeResponse> children = new LinkedList<OrgNodeResponse>();
        // 3 循环将下级节点数据添加进来
        Iterator<YzjSysDepartmentTest> it = parentIdList.iterator();
        while (it.hasNext()) {
            YzjSysDepartmentTest t = it.next();
            if (StringUtils.isNotBlank(t.getParent_id()+"")&&
                    t.getParent_id().equals(parent_id)) {
                OrgNodeResponse node = new OrgNodeResponse();
                node.setId(t.getId()+"");
                node.setName(t.getName());
                node.setHref("#");
                node.setSpread(true);
                node.setChildren(getChildrenNodes(Integer.parseInt(node.getId()), parentIdList));
                children.add(node);
            }
        }

        return children;
    }


}
