package com.jc.websocket;

import org.apache.ibatis.annotations.Param;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 年: 2019
 * 月: 09
 * 日: 17
 * 小时: 10
 * 分钟: 04
 *
 * @author 严脱兔
 */
//带参数
//@ServerEndpoint("/websocket/{uid}")
//不带参数
//@ServerEndpoint("/websocket")
public class MyWebSocket {

    //不带参数
    // public void onOpen(Session session) throws IOException {
    //带参数
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid")String uid) throws IOException {
        System.out.println("websocket已经连接" +session);
        session.getBasicRemote().sendText(uid+",您好！欢迎登陆系统");
    }
    @OnClose
    public void onClose(Session session){
        System.out.println("websocket已经关闭" +session);
    }
    @OnMessage
    public  void onMessage(String message,Session session)throws IOException {
        System.out.println("收到客户端发来的消息-->" +message);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.getBasicRemote().sendText("消息已收到");
    }
}
