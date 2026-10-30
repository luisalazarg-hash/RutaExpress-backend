package com.rutaexpress.ms_notificaciones.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String TOPIC_EXCHANGE = "cmd.topic";
    public static final String DIRECT_EXCHANGE = "cmd.direct";
    public static final String DLX_EXCHANGE = "cmd.dead.dlx";
    public static final String CREATED_QUEUE = "shipment.created.queue";
    public static final String STATUS_QUEUE = "shipment.status.changed.queue";
    public static final String DETAIL_QUEUE = "shipment.detail.queue";
    public static final String CREATED_DLQ = "shipment.created.dlq";
    public static final String STATUS_DLQ = "shipment.status.changed.dlq";
    public static final String DETAIL_DLQ = "shipment.detail.dlq";

    @Bean
    public TopicExchange commandTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange commandDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE, true, false);
    }

    @Bean
    public Queue shipmentCreatedQueue() {
        return workQueue(CREATED_QUEUE, CREATED_DLQ);
    }

    @Bean
    public Queue shipmentStatusQueue() {
        return workQueue(STATUS_QUEUE, STATUS_DLQ);
    }

    @Bean
    public Queue shipmentDetailQueue() {
        return workQueue(DETAIL_QUEUE, DETAIL_DLQ);
    }

    @Bean
    public Queue shipmentCreatedDlq() { return new Queue(CREATED_DLQ, true); }

    @Bean
    public Queue shipmentStatusDlq() { return new Queue(STATUS_DLQ, true); }

    @Bean
    public Queue shipmentDetailDlq() { return new Queue(DETAIL_DLQ, true); }

    @Bean
    public Binding createdBinding(Queue shipmentCreatedQueue, TopicExchange commandTopicExchange) {
        return BindingBuilder.bind(shipmentCreatedQueue).to(commandTopicExchange).with("shipment.created");
    }

    @Bean
    public Binding statusBinding(Queue shipmentStatusQueue, TopicExchange commandTopicExchange) {
        return BindingBuilder.bind(shipmentStatusQueue).to(commandTopicExchange).with("shipment.status.changed");
    }

    @Bean
    public Binding detailBinding(Queue shipmentDetailQueue, TopicExchange commandTopicExchange) {
        return BindingBuilder.bind(shipmentDetailQueue).to(commandTopicExchange).with("shipment.detail.generated");
    }

    @Bean
    public Binding createdDlqBinding(Queue shipmentCreatedDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(shipmentCreatedDlq).to(deadLetterExchange).with(CREATED_DLQ);
    }

    @Bean
    public Binding statusDlqBinding(Queue shipmentStatusDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(shipmentStatusDlq).to(deadLetterExchange).with(STATUS_DLQ);
    }

    @Bean
    public Binding detailDlqBinding(Queue shipmentDetailDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(shipmentDetailDlq).to(deadLetterExchange).with(DETAIL_DLQ);
    }

    private Queue workQueue(String name, String deadLetterRoutingKey) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DLX_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
        return new Queue(name, true, false, false, arguments);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
