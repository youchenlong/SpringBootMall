package com.example.springbootmall.listener;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class OrderTimeOutListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OmsOrderService omsOrderService;

    @Value("${redis.key.prefix.order}")
    private String REDIS_KEY_PREFIX_ORDER;

    @Autowired
    public OrderTimeOutListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
//        String expiredKey = message.toString();
//        if (expiredKey.startsWith(REDIS_KEY_PREFIX_ORDER + "orderId:")) {
//            Long orderId = Long.parseLong(expiredKey.substring((REDIS_KEY_PREFIX_ORDER + "orderId:").length()));
//            OmsOrder order = omsOrderService.getOrderById(orderId);
//            omsOrderService.cancelOrder(order);
//        }
    }
}
