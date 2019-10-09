//package com.jc.socket;
//
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 年: 2019
// * 月: 09
// * 日: 18
// * 小时: 16
// * 分钟: 55
// *
// * @author 严脱兔
// */
//public class TextMessageHandler extends TextWebSocketHandler {
//
//    private static final Map<String, WebSocketSession> users;
//
//    static {
//        users = new HashMap<String, WebSocketSession>();
//    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        /*
//         * 链接成功后触发此方法
//         */
//        users.put(session.getId(), session);
//        String username = (String) session.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME);
//        session.sendMessage(new TextMessage(username + " 链接成功!!"));
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        super.handleMessage(session, message);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        /*
//         * 前台连接时会触发此方法
//         */
//        super.handleTextMessage(session, message);
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        if (session.isOpen()) {
//            session.close();
//        }
//        System.out.println("关闭websocket连接......");
//        users.remove(session.getId());
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        System.out.println("连接已关闭......");
//        users.remove(session.getId());
//    }
//
//    /**
//     * 发送消息给指定用户(或群发所有用户)
//     * @param username
//     * @param message
//     */
//    public void sendMessageToUser(String username, TextMessage message) {
//        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
//        while (it.hasNext()) {
//            WebSocketSession session = it.next().getValue();
//            if (username.equals(session.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME))) {
//                try {
//                    if (session.isOpen())
//                        session.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    /**
//     * 群发所有用户
//     * @param message
//     */
//    public void sendMessageToUsers(TextMessage message) {
//        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
//        while (it.hasNext()) {
//            WebSocketSession session = it.next().getValue();
//            try {
//                if (session.isOpen())
//                    session.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//    private Iterator<Map.Entry<String, WebSocketSession>> userIterator() {
//        Set<Map.Entry<String, WebSocketSession>> entrys = users.entrySet();
//        return entrys.iterator();
//    }
//}
