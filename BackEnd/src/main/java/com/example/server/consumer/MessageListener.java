package com.example.server.consumer;

import com.example.server.model.response.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Component;

import static com.example.server.constant.Constant.*;

import java.security.Principal;

import com.example.server.dao.UserDao;

@Component
public class MessageListener {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    UserDao userDao;
    
    @KafkaListener(
        topics = KAFKA_TOPIC_COMMENT,
       groupId = GROUP_ID
    )
    public void listenComment(@Payload CommentMessageResponse message) {
        template.convertAndSend("/channel/contribution/"+message.getContributionId().toString(), message);
    }

    @KafkaListener(
        topics = KAFKA_TOPIC_CHAT,
       groupId = GROUP_ID
    )
    @SubscribeMapping ( "/user/queue/chat" )
    public void listenChatMessage(@Payload ChatMessageResponse message) {
        template.convertAndSendToUser(message.getToUserName(), "/queue/chat", message);
    }
}
