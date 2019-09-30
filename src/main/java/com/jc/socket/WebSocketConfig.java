package com.jc.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket的配置类 springBoot也可用
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    //注入socket的处理类
    @Autowired
    private SocketHandler socketHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 实现对应的websocket配置, 将对应的webSocket的处理类进行信息的注册
        // 注册socket信息需要传入对应的socket类和对应的socket拦截器
        //注册处理拦截器,拦截url为socketServer的请求
        registry.addHandler(socketHandler,"/websocket/socketServer.do").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");

        //注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
        // 如果有浏览器不支持默认的websocket, 需要去下载sockjs这个js包
        registry.addHandler(socketHandler, "/sockjs/socketServer.do").addInterceptors(new WebSocketInterceptor()).withSockJS();
    }
}
