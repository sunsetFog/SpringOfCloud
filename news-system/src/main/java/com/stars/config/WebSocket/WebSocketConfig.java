package com.stars.config.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
// 加这个启动不了了
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //   客户端连接 ws = new WebSocket('ws://localhost:8080/websocket');
        registry.addHandler(new MyWebSocketHandler(), "/websocket").setAllowedOrigins("*");
    }
}
