package com.jc.socket;

import com.jc.mapper.MessageMapper;
import com.jc.mapper.SocketHandlerMapper;
import com.jc.model.Message;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * websocket的处理类
 * 需要实现spring对应提供的socket接口
 */
@Component
public class SocketHandler implements WebSocketHandler {

    @Resource
    private SocketHandlerMapper socketHandlerMapper;

    private static final Logger logger;
    private static final ArrayList<WebSocketSession> users;

    static{
        users = new ArrayList<WebSocketSession>();
        logger = Logger.getLogger(SocketHandler.class);
    }


    /**
     * 连接建立后会走的方法 触发页面上的open
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("成功建立socket连接");
        // 将得到的用户信息存入users中(其实就是在线用户)
        users.add(webSocketSession);
       if ( webSocketSession.getAttributes().get("currentUser")!= null){
           webSocketSession.getAttributes().get("currentUser").toString();
       }

        // 给在线用户发送信息(也可以把给在线用户发送信息这件事情封装成一个方法)
        for (WebSocketSession user : users) {
            user.sendMessage(new TextMessage("成功建立连接"));
        }
    }

    /*
    信息的处理
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("来自客户端的消息");
    }

    /*
    当信息出现错误的时候执行的方法
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.error("建立连接错误");
//        关闭socket会话连接
        webSocketSession.close();
        //连接出现错误要移除当前所有的在线用户
        users.remove(webSocketSession);
    }

    /*
    连接关闭的时候执行的方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("连接已经成功关闭");
        // 连接关闭也需要移除当前所有的在线用户
        users.remove(webSocketSession);
    }

    /*
    载体消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 群发自定义消息
     * */
    public  void sendInfo(String message)  {
        logger.info(message);
        for (WebSocketSession item : users) {
            try {
                item.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 发送消息给某个人
     */
    // String username 其实就是标识某个用户的key
    public void sendForOne(String message_type,String message,String id) {
        SysLoginUser loaduser = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int from_user_id = loaduser.getId();
        String to_user_ids = String.valueOf(id);
        String message_content = message;
        Date modify_date = new Date();
        SysLoginUser sysLoginUser = socketHandlerMapper.loadUser(id);
        String name = sysLoginUser.getAccount_name();
        Message loadmessage = new Message(modify_date,"27",message_type,message_content,from_user_id,to_user_ids);
        for (WebSocketSession user : users) {
            String username_key =(String) user.getAttributes().get("currentUser");
            if (name.equals(username_key)) {
                socketHandlerMapper.insertMessage(loadmessage);
                //给当前对应的用户发送消息
                try {
                    user.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
