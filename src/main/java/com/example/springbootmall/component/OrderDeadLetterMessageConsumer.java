package com.example.springbootmall.component;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.service.OmsOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderDeadLetterMessageConsumer {
    @Autowired
    private OmsOrderService omsOrderService;

    @RabbitListener(queues = "${rabbitmq.ORDER_DEAD_LETTER_QUEUE_NAME}")
    public void receive(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        System.out.println("receive dead letter message: " + msg);
        Long orderId = Long.valueOf(msg);
        OmsOrder order = omsOrderService.getOrderById(orderId);
        System.out.println(order);
        // 待支付
        if (order != null && order.getStatus() == 0) {
            System.out.println("订单超时");
            omsOrderService.cancelOrder(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
