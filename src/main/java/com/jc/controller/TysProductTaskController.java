package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.ProduceTask;
import com.jc.model.ProduceTaskDetail;
import com.jc.model.SysUsers;
import com.jc.service.SysUsersService;
import com.jc.service.TysProductTaskDetailService;
import com.jc.service.TysProductTaskService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/T_ProduceTask")
public class TysProductTaskController {
    @Resource
    TysProductTaskService tysProductTaskServiceImpl;
    @Autowired
    TysProductTaskDetailService tysProductTaskDetailServiceImpl;
    @Autowired
    SysUsersService sysUsersServiceImpl;
    @RequestMapping(value = "/listProductTask.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult listProduceTask(String page,String limit){
        List<ProduceTask> produceTasks = tysProductTaskServiceImpl.listProduceTask( page, limit );
        return new PageResultBean<Collection<ProduceTask>>( produceTasks,tysProductTaskServiceImpl.countProduceTask());
    }
    @RequestMapping(value = "/saveProductTask.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult saveProductTask(@RequestBody ProduceTask produceTask){
        produceTask.setCreator( "1" );
        produceTask.setModifier( "1" );
        produceTask.setState( "未审核" );
        Date date = new Date(  );
        produceTask.setCreate_date( date );
        produceTask.setModify_date( date );
        tysProductTaskServiceImpl.saveProduceTask( produceTask );
        List<ProduceTaskDetail> listTaskDetail = produceTask.getListTaskDetail();
        for (ProduceTaskDetail produceTaskDetail : listTaskDetail) {
            produceTaskDetail.setProduce_task_id( produceTask.getId() );
            tysProductTaskDetailServiceImpl.saveProduceTaskDetail( produceTaskDetail );
        }
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/deleteProductTask.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult deleteProductTask(int id){
        tysProductTaskDetailServiceImpl.removeProduceTaskDetail( id );
        tysProductTaskServiceImpl.removeProduceTask( id );
//        List<ProduceTask> produceTasks = tysProductTaskServiceImpl.listProduceTask( page, limit );
        return new ResultBean<String>( "success");
    }
    @RequestMapping(value = "/loadProductTask.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadProductTask(int id){
        ProduceTask produceTask = tysProductTaskServiceImpl.loadProduceTask( id );
        List<ProduceTaskDetail> produceTaskDetails = tysProductTaskDetailServiceImpl.listProduceTaskDetail( id );
        produceTask.setListTaskDetail( produceTaskDetails );

//        List<ProduceTask> produceTasks = tysProductTaskServiceImpl.listProduceTask( page, limit );
        return new ResultBean<ProduceTask>(produceTask);
    }
    @RequestMapping(value = "/UpdateProductTask.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult UpdateProductTask(@RequestBody ProduceTask produceTask){
        produceTask.setState("未审核" );
        Date date = new Date(  );
        produceTask.setModify_date( date);
        tysProductTaskServiceImpl.updateProduceTask( produceTask ) ;
        List<Integer> list = new ArrayList(  ) ;
        List<ProduceTaskDetail> produceTaskDetails = tysProductTaskDetailServiceImpl.listProduceTaskDetail( produceTask.getId() );
        List<ProduceTaskDetail> listTaskDetail = produceTask.getListTaskDetail();
        for (ProduceTaskDetail produceTaskDetail : produceTaskDetails) {
            list.add(Integer.valueOf(produceTaskDetail.getProduct_id()));
        }
        for (ProduceTaskDetail produceTaskDetail : listTaskDetail) {
            produceTaskDetail.setProduce_task_id( produceTask.getId() );
            int productid = Integer.valueOf(produceTaskDetail.getProduct_id());
            if (list.contains( productid )){
                for (ProduceTaskDetail produceTaskDetail1 : produceTaskDetails) {
                   if(productid==Integer.valueOf(produceTaskDetail1.getProduct_id())){
                       produceTaskDetail.setId( produceTaskDetail1.getId() );
                   }
                }
                tysProductTaskDetailServiceImpl.updateProduceTaskDetail( produceTaskDetail );
                list.remove( (Integer)productid );
            }else{
                tysProductTaskDetailServiceImpl.saveProduceTaskDetail( produceTaskDetail );
            }
        }
        //根据唯一键id删除
        for (Integer integer : list) {
            for (ProduceTaskDetail produceTaskDetail : produceTaskDetails) {
                if (integer==Integer.valueOf(produceTaskDetail.getProduct_id())){
                    tysProductTaskDetailServiceImpl.removeTyProductTaskDetail( produceTaskDetail.getId() );
                }
            }
        }
//        List<TysProduceBomDetail> tysProduceBomDetails = tysProductBomDetailServiceImpl.listtysproduceDetail( tysProduceBom.getId() );
//        List<TysProduceBomDetail> listBomDetail = tysProduceBom.getListBomDetail();
//        for (TysProduceBomDetail produceBomDetail : tysProduceBomDetails) {
//            list.add( produceBomDetail.getProduct_id() );
//        }
//        for (TysProduceBomDetail tysProduceBomDetail : listBomDetail) {
//            tysProduceBomDetail.setProduct_bom_id(tysProduceBom.getId());
//            int id = tysProduceBomDetail.getProduct_id();
//            if (list.contains( id )){
//                tysProductBomDetailServiceImpl.UpdateTysProduceBomDetail( tysProduceBomDetail );
//                list.remove((Integer)id);
//            }else {
//                tysProductBomDetailServiceImpl.saveTysProductBomDetail( tysProduceBomDetail );
//            }
//        }
//        for (Integer integer : list) {
//            tysProductBomDetailServiceImpl.removeProduceBomDetail(integer);
//        }
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/UpdateProductTaskAuditing.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult UpdateProductTaskAuditing(int id){
        tysProductTaskServiceImpl.updateProduceTaskAuditing( id );
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/UpdateProductTaskReject.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult UpdateProductTaskReject(int id){
        tysProductTaskServiceImpl.updateProduceTaskReject( id );
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/ListSysUsersName.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult ListSysUsersName(){
        List<SysUsers> sysUsers = sysUsersServiceImpl.listSysUsers();
        return new ResultBean<Collection<SysUsers>>(sysUsers);
    }
}
