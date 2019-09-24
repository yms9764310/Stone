//package com.jc.controller;
//
//import com.jc.beans.response.IResult;
//import com.jc.beans.response.PageResultBean;
//import com.jc.model.Message;
//import com.jc.service.MessageService;
//import com.jc.socket.TextMessageHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.socket.TextMessage;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Collection;
//import java.util.List;
//
///**
// * 年: 2019
// * 月: 08
// * 日: 21
// * 小时: 15
// * 分钟: 25
// *
// * @author 严脱兔
// */
//@Slf4j
//@Controller
//@RequestMapping("Message")
//public class MessageController {
//    @Resource
//    private MessageService messageServiceImpl;
//    @Bean
//    public TextMessageHandler textMessageHandler() {
//        return new TextMessageHandler();
//    }
//
//    @RequestMapping
//    public String view() {
//        return "Message";
//    }
//
//    /**
//     * 发送消息到页面
//     * @param request
//     * @param username
//     * @return
//     */
//    @RequestMapping("Send")
//    @ResponseBody
//    public String send(HttpServletRequest request, @RequestParam("username") String username) {
//        TextMessage message = new TextMessage(request.getParameter("message"));
//        textMessageHandler().sendMessageToUser(username, message);
//        return "true";
//    }
//
//    /**
//     * 查询待审核事项
//     * */
//    @RequestMapping("/findMessageList")
//    @ResponseBody
//    public IResult listMessage(String page, String limit,String name,String message_type){
//        //返回json至前端的均返回ResultBean或者PageResultBean
//        List<Message> resultData = messageServiceImpl.listMessage(page,limit,name,message_type);
//        return new PageResultBean<Collection<Message>>(resultData,resultData.size());
//    }
//
//}
