package com.example.springbootmall.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitTemplateConfig.class);

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("send message success, correlationData({}), ack({}), cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("return callback");
            log.debug("return message: {}", returned.getMessage());
            log.debug("return exchange: {}", returned.getExchange());
            log.debug("return routingKey: {}", returned.getRoutingKey());
            log.debug("return replyCode: {}", returned.getReplyCode());
            log.debug("return replyText: {}", returned.getReplyText());
        });
        return rabbitTemplate;
    }
}
