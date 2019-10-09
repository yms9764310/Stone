package com.jc.serviceImpl;

;
import com.jc.beans.response.PageRange;
import com.jc.beans.response.ResultBean;
import com.jc.mapper.YzjSysLoginUserTestMapper;
import com.jc.mapper.YzjSysPurchaseProductMapper;
import com.jc.model.SysLoginUser;

import com.jc.model.YzjSysPurchaseProduct;
import com.jc.model.YzjSysResource;
import com.jc.service.YzjSysPurchaseProductService;
import com.jc.util.ExcelUtils;
import org.apache.shiro.SecurityUtils;
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
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class YzjSysPurchaseProductServiceImpl implements YzjSysPurchaseProductService {
    @Resource
    private YzjSysPurchaseProductMapper yzjSysPurchaseProductMapper;
    @Resource
    private YzjSysLoginUserTestMapper yzjSysLoginUserTestMapper;

    @Override
    public List<YzjSysPurchaseProduct> listYzjSysPurchaseProduct(String page, String limit) {
        PageRange pageRange = new PageRange(page,limit);
        return yzjSysPurchaseProductMapper.listYzjSysPurchaseProduct(pageRange.getStart(),pageRange.getEnd());

    }

    @Override
    public int countYzjSysPurchaseProduct() {
        return yzjSysPurchaseProductMapper.countYzjSysPurchaseProduct();
    }

    @Override
    public int deleteYzjSysPurchaseProduct(int id) {
        return yzjSysPurchaseProductMapper.deleteYzjSysPurchaseProduct(id);
    }

    @Override
    public Integer updateYzjSysPurchaseProduct(YzjSysPurchaseProduct yzjSysPurchaseProduct) {
//        Date date = new Date();//获取时间
//        int id = 1;
//        //获取登录人员id
//        int pid = yzjSysPurchaseProduct.getId();
//        //获取登录人员的名称
//        YzjSysLoginUser yzjSysLoginUser = yzjSysLoginUserTestMapper.loadLoginUserById(id);
//        //获取editProu页面传来的值
//        Date create_date = yzjSysPurchaseProduct.getCreate_date();
//        String name = yzjSysPurchaseProduct.getName();
//        String kind = yzjSysPurchaseProduct.getKind();
//        String model_type = yzjSysPurchaseProduct.getModel_type();
//        String standard = yzjSysPurchaseProduct.getStandard();
//        String description = yzjSysPurchaseProduct.getDescription();
//        YzjSysPurchaseProduct yzjSysPurchaseProduct1 = new YzjSysPurchaseProduct(pid,yzjSysLoginUser.getCreator(),create_date,yzjSysLoginUser.getModifier(),date,"很好",name,kind,model_type,standard,description);
        Date date = new Date();

        yzjSysPurchaseProduct.setModify_date(date);
        yzjSysPurchaseProduct.setState("很奈斯");
        yzjSysPurchaseProductMapper.updateYzjSysPurchaseProduct(yzjSysPurchaseProduct);
        return 0;
    }

    @Override
    public YzjSysPurchaseProduct loadYzjSysPurchaseProductById(int id) {
        return yzjSysPurchaseProductMapper.loadYzjSysPurchaseProductById(id);
    }

    @Override
    public Integer saveYzjSysPurchaseProduct(YzjSysPurchaseProduct yzjSysPurchaseProduct) {
//        Date date = new Date();//获取时间
//        int id = 1;
//        //获取登录人员id
//        YzjSysLoginUser yzjSysLoginUser = yzjSysLoginUserTestMapper.loadLoginUserById(id);
//        //获取addProu编辑页面传过来的值
//        String name = yzjSysPurchaseProduct.getName();
//        String kind = yzjSysPurchaseProduct.getKind();
//        String model_type = yzjSysPurchaseProduct.getModel_type();
//        String standard = yzjSysPurchaseProduct.getStandard();
//        String description = yzjSysPurchaseProduct.getDescription();
//        YzjSysPurchaseProduct yzjSysPurchaseProduct1 = new YzjSysPurchaseProduct(yzjSysLoginUser.getCreator(),date,yzjSysLoginUser.getModifier(),date,"很好",name,kind,model_type,standard,description);
//        yzjSysPurchaseProductMapper.saveYzjSysPurchaseProduct(yzjSysPurchaseProduct1);
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysLoginUser yzjSysLoginUser = yzjSysLoginUserTestMapper.loadLoginUserById(id);
        yzjSysPurchaseProduct.setCreator(id);
        yzjSysPurchaseProduct.setCreate_date(date);
        yzjSysPurchaseProduct.setModifier(id);
        yzjSysPurchaseProduct.setModify_date(date);
        yzjSysPurchaseProduct.setState("很奈斯");
        yzjSysPurchaseProductMapper.saveYzjSysPurchaseProduct(yzjSysPurchaseProduct);
        return 1;
    }


    @Override
    public String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);//ajax提交，做文件上传时，由于上传的文件是另一台服务器

        MultipartFile file = multipartRequest.getFile("upfile");
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            YzjSysPurchaseProduct vo = new YzjSysPurchaseProduct();



            int id = 1;
            SysLoginUser seleccid = yzjSysLoginUserTestMapper.loadLoginUserById(id);
            //根据登录表中的人员名称，匹配excle内容，将对应的创建人存入对象
            vo.setCreator(seleccid.getCreator());

            vo.setCreate_date(new Date());
            //修改人
            vo.setModifier(seleccid.getModifier());

            vo.setModify_date(new Date());
            // 表格的第一列   注意数据格式需要对应实体类属性
            //状态
            vo.setState(String.valueOf(lo.get(0)));
            //商品名称
            vo.setName(String.valueOf(lo.get(1)));
            //商品类型
            vo.setKind(String.valueOf(lo.get(2)));
            //商品型号
            vo.setModel_type(String.valueOf(lo.get(3)));
            //商品规格
            vo.setStandard(String.valueOf(lo.get(4)));
            //描述
            vo.setDescription(String.valueOf(lo.get(5)));
            yzjSysPurchaseProductMapper.saveYzjSysPurchaseProduct(vo);
        }
        return "success";
    }


}
