package com.mc.middleware.config;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfigration {

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.consumer.session.timeout}")
    private int consumerSessionTimeout;
    @Value("${kafka.consumer.max-poll-records}")
    private int consumerMaxPollRecords;
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String consumerKeyDeserializer;
    @Value("${spring.kafka.consumer.value-deserializer}")
    private String consumerValueDeserializer;
    @Value("${spring.kafka.producer.key-serializer}")
    private String producerKeySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String producerValueSerializer;
    @Value("${kafka.producer.max-block-time}")
    private String producerMaxBlockTime;
    @Value("${common.client.security-protocol}")
    private String clientSecurityProtocol;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
            buildKafkaListenContainerFactory() {

        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configureConsumer());
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        concurrentKafkaListenerContainerFactory.setBatchListener(true);

        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> buildKafkaTemplate() {
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(configureConsumer());

        return new KafkaTemplate<>(producerFactory);
    }

    private Map<String, Object> configureConsumer() {

        Map<String, Object> properties = Maps.newHashMap();
        // 设置接入点，请通过控制台获取对应的Topic接入点
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        // 两次poll之间的最大允许间隔，请不要设置太大，服务器会掐掉空闲连接，不要超过30000
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.consumerSessionTimeout);
        // 每次Poll的数量，注意值不要设置过大，如果poll太多数据而不能在下次poll之前消费完，则会触发一次负载均衡，产生卡顿
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, this.consumerMaxPollRecords);
        // 消息反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, this.consumerKeyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, this.consumerValueDeserializer);
        // 消息序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.producerKeySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.producerValueSerializer);
        // 请求的最长等待时间
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, this.producerMaxBlockTime);
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, this.clientSecurityProtocol);

        return properties;
    }
}
