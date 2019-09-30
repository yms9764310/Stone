package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.model.Message;
import com.jc.service.MessageService;
import com.jc.socket.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 15
 * 分钟: 25
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/Message")
public class MessageController {

    // 注入webSocket的处理类
    @Autowired
    private SocketHandler socketHandler;

    @Resource
    private MessageService messageServiceImpl;


    @RequestMapping
    public String view() {
        return "Message";
    }

    /**
     * 发送消息到页面
     */
    @RequestMapping("/send")
    @ResponseBody
    public String messageTest() {
//    发送消息到前台
        socketHandler.sendInfo("后台controller发送");
//        so
        return "message is ok";
    }

    @RequestMapping("/login")
    public String login(String username, String pwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("USER_KEY", username);
        return "SocketDemo";
    }

    @RequestMapping("/messgaOne")
    @ResponseBody
    public String messageOne(String username) {

        System.out.println("提交成功了");
        socketHandler.sendForOne("类型","这是我发给你的", username);
        return "success";
    }

    /**
     * 查询待审核事项
     * */
    @RequestMapping("/findMessageList")
    @ResponseBody
    public IResult listMessage(String page, String limit,String name,String message_type){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<Message> resultData = messageServiceImpl.listMessage(page,limit,name,message_type);
        return new PageResultBean<Collection<Message>>(resultData,resultData.size());
    }

}
