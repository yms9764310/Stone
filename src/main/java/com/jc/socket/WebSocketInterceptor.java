package com.jc.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * webSocket的拦截器
 * 需要实现握手协议的拦截
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        // 这里是在进入消息处理前要做的事情, 一般来说会校验用户是否登陆, 此处暂时可以不用校验直接返回true就好了
        // true为放行, false为阻止

        if(serverHttpRequest instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletRequest.getServletRequest().getSession();
            if(session!=null){
                //区分socket连接以定向发送消息
                if (session.getAttribute("currentUser") != null) {
                    map.put("currentUser", session.getAttribute("currentUser"));
                }

            }
        }


        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
