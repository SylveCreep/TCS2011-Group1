package com.example.server.config.chatconfig;

import com.example.server.config.TokenProvider;
import com.example.server.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import static com.example.server.constant.Constant.*;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketAuthenticationConfig.class);


    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String authToken = authorization.get(0).split(" ")[1];
                    String username = jwtTokenUtil.getUsernameFromToken(authToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (authToken != null && jwtTokenUtil.validateToken(authToken, userDetails)) {
				        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        Principal principal = authentication;
                        if (Objects.isNull(principal)){
                            return null;
                        }
                        userService.updateOnlineStatus(ONLINE, accessor.getSessionId());
                        accessor.setUser(principal);
                    }
                } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    userService.updateOnlineStatus(OFFLINE, accessor.getSessionId());
                    if (Objects.nonNull(authentication))
                        logger.info("Disconnected Auth : " + authentication.getName());
                    else
                        logger.info("Disconnected Sess : " + accessor.getSessionId());
                }
                return message;
            }
        });
    }
}
