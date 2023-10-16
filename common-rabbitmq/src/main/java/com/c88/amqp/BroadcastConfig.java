package com.c88.amqp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastConfig {

    private static final boolean NON_DURABLE = false;

    public static final String FANOUT_QUEUE = "fanout.event.queue";
    public static final String FANOUT_EXCHANGE_EVENT = "fanout.exchange.event";

    public static final String TOPIC_LOGIN_SUCCESS_QUEUE = "topic.login.success.queue";
    public static final String TOPIC_LOGIN_ERROR_QUEUE = "topic.login.error.queue";
    public static final String TOPIC_SAVE_BET_QUEUE = "topic.save.bet.queue";

    public static final String TOPIC_EXCHANGE_EVENT = "topic.exchange.event";

    public static final String TOPIC_ANNO_LOG_QUEUE = "topic.anno.log.queue";


    public static final String BINDING_LOGIN_EVENT_SUCCESS = "#.login.success";
    public static final String BINDING_LOGIN_EVENT_ERROR = "#.login.error";
    public static final String BINDING_SAVE_BET_EVENT = "#.save.betOrder";

    public static final String BINDING_ANNO_LOG_EVENT = "#.anno.log";

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(om);
        messageConverter.setClassMapper(classMapper());
        return messageConverter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setDefaultRequeueRejected(Boolean.TRUE);
        return factory;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

    @Bean
    public Declarables topicBindings() {
        Queue topicLoginSuccessQueue = new Queue(TOPIC_LOGIN_SUCCESS_QUEUE, NON_DURABLE);
        Queue topicLoginErrorQueue = new Queue(TOPIC_LOGIN_ERROR_QUEUE, NON_DURABLE);
        Queue topicSaveBetQueue = new Queue(TOPIC_SAVE_BET_QUEUE, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_EVENT, NON_DURABLE, false);
        Queue topicAnnoLogQueue = new Queue(TOPIC_ANNO_LOG_QUEUE);

        return new Declarables(topicLoginSuccessQueue, topicLoginErrorQueue, topicSaveBetQueue, topicExchange, topicAnnoLogQueue,
                BindingBuilder
                        .bind(topicLoginSuccessQueue)
                        .to(topicExchange)
                        .with(BINDING_LOGIN_EVENT_SUCCESS),
                BindingBuilder
                        .bind(topicLoginErrorQueue)
                        .to(topicExchange)
                        .with(BINDING_LOGIN_EVENT_ERROR),
                BindingBuilder.bind(topicSaveBetQueue)
                        .to(topicExchange)
                        .with(BINDING_SAVE_BET_EVENT),
                BindingBuilder
                        .bind(topicAnnoLogQueue)
                        .to(topicExchange)
                        .with(BINDING_ANNO_LOG_EVENT));
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue = new Queue(FANOUT_QUEUE, NON_DURABLE);

        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_EVENT, NON_DURABLE, false);

        return new Declarables(fanoutQueue, fanoutExchange, BindingBuilder
                .bind(fanoutQueue)
                .to(fanoutExchange));
    }

}
