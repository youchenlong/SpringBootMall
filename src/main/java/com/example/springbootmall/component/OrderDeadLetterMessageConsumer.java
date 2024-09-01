package com.example.springbootmall.component;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.service.OmsOrderService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderDeadLetterMessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderDeadLetterMessageConsumer.class);
    @Autowired
    private OmsOrderService omsOrderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    private int retryMaxAttempts;

    @RabbitListener(queues = "${rabbitmq.ORDER_DEAD_LETTER_QUEUE_NAME}")
    public void receive(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        Long orderId = Long.valueOf(msg);
        try {
            OmsOrder order = omsOrderService.getOrderById(orderId);
            // 待支付
            if (order != null && order.getStatus() == 0) {
                log.info("订单超时，取消订单");
                omsOrderService.cancelOrder(order);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
        catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
