package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysUsers;
import com.jc.model.UserHistory;
import com.jc.service.PersonLogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 26
 * 小时: 14
 * 分钟: 26
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/PersonLogHistory")
public class PersonLogHistoryController {
    @Resource
    private PersonLogHistoryService personLogHistoryServiceImpl;


    /**
     * 查询全部日志记录
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult loadUsers(String page, String limit, String startTime, String endTime,String name, String description){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<UserHistory> userHistories = personLogHistoryServiceImpl.listUserHistoryAll(page, limit, startTime, endTime, name, description);
        int historyNum = personLogHistoryServiceImpl.countGetAll();
        return new PageResultBean<Collection<UserHistory>>(userHistories,historyNum);
    }

    /**
     * 删除日志记录
     * */
    @RequestMapping("/delete")
    @ResponseBody
    public IResult deleteLogHistory(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        int i = personLogHistoryServiceImpl.deleteLogHistory(id);
        return new ResultBean<Integer>(i);
    }
}
