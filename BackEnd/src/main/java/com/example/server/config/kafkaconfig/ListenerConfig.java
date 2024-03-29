package com.example.server.config.kafkaconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import com.example.server.model.response.ChatMessageResponse;
import com.example.server.model.response.CommentMessageResponse;

import static com.example.server.constant.Constant.*;

@EnableKafka
@Configuration
public class ListenerConfig {
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CommentMessageResponse> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CommentMessageResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, CommentMessageResponse> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(CommentMessageResponse.class));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ChatMessageResponse> kafkaListenerContainerChatMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerChatMessageFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ChatMessageResponse> consumerChatMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(ChatMessageResponse.class));
    }

    @Bean
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return configurations;
    }
}
