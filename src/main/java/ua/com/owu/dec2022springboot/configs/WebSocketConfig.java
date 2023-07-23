package ua.com.owu.dec2022springboot.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
//@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
//    private WebSocketConnection webSocketConnection;

    @Bean
    public WebSocketConnection webSocketConnection() {
        return new WebSocketConnection();
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketConnection(), "/chat") //якщо без  @Bean WebSocketConnection то webSocketConnection
                .setAllowedOrigins("*");
    }
}
