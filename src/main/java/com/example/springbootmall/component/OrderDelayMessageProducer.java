package com.example.springbootmall.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderDelayMessageProducer {
    private static final Logger log = LoggerFactory.getLogger(OrderDelayMessageProducer.class);
    @Value("${rabbitmq.ORDER_DELAY_EXCHANGE_NAME}")
    private String ORDER_DELAY_EXCHANGE_NAME;
    @Value("${rabbitmq.ORDER_DELAY_ROUTING_KEY}")
    private String ORDER_DELAY_ROUTING_KEY;
    @Value("${rabbitmq.expiration}")
    private String expiration;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(ORDER_DELAY_EXCHANGE_NAME, ORDER_DELAY_ROUTING_KEY, message, msg -> {
            msg.getMessageProperties().setExpiration(expiration);
            return msg;
        });
    }
}
