package com.example.springbootmall.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.ORDER_DELAY_EXCHANGE_NAME}")
    private String ORDER_DELAY_EXCHANGE_NAME;
    @Value("${rabbitmq.ORDER_DELAY_QUEUE_NAME}")
    private String ORDER_DELAY_QUEUE_NAME;
    @Value("${rabbitmq.ORDER_DELAY_ROUTING_KEY}")
    private String ORDER_DELAY_ROUTING_KEY;
    @Value("${rabbitmq.ORDER_DEAD_LETTER_EXCHANGE_NAME}")
    private String ORDER_DEAD_LETTER_EXCHANGE_NAME;
    @Value("${rabbitmq.ORDER_DEAD_LETTER_QUEUE_NAME}")
    private String ORDER_DEAD_LETTER_QUEUE_NAME;
    @Value("${rabbitmq.ORDER_DEAD_LETTER_ROUTING_KEY}")
    private String ORDER_DEAD_LETTER_ROUTING_KEY;
    @Value("${rabbitmq.ORDER_DELAY_QUEUE_TTL}")
    private Integer ORDER_DELAY_QUEUE_TTL;

    @Bean("orderDelayQueue")
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DEAD_LETTER_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", ORDER_DEAD_LETTER_ROUTING_KEY);
        args.put("x-message-ttl", ORDER_DELAY_QUEUE_TTL);
        return QueueBuilder.durable(ORDER_DELAY_QUEUE_NAME).withArguments(args).build();
    }
    @Bean("orderDelayExchange")
    public DirectExchange exchange() {
        return new DirectExchange(ORDER_DELAY_EXCHANGE_NAME);
    }
    @Bean("orderDeadLetterQueue")
    public Queue deadLetterQueue() {
        return new Queue(ORDER_DEAD_LETTER_QUEUE_NAME);
    }
    @Bean("orderDeadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(ORDER_DEAD_LETTER_EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(@Qualifier("orderDelayQueue") Queue queue, @Qualifier("orderDelayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DELAY_ROUTING_KEY);
    }
    @Bean
    public Binding deadLetterBinding(@Qualifier("orderDeadLetterQueue") Queue queue, @Qualifier("orderDeadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DEAD_LETTER_ROUTING_KEY);
    }
}
