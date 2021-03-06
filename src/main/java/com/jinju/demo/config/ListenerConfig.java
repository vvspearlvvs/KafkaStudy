package com.jinju.demo.config;
 import com.jinju.demo.constants.KafkaConstants;
 import com.jinju.demo.model.ChattingMessage;
 import org.apache.kafka.clients.consumer.ConsumerConfig;
 import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
 import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
 import org.springframework.kafka.core.*;
 import org.apache.kafka.common.serialization.StringDeserializer;
 import org.springframework.kafka.support.serializer.JsonDeserializer;
 import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
//receive message by topic
public class ListenerConfig {
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,  JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return configurations;
    }
    @Bean
    public ConsumerFactory<String,ChattingMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(ChattingMessage.class));
    }

}
