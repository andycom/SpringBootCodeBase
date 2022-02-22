package com.fancv.mq;


import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * hamish
 * www.fancv.com
 */
@RocketMQMessageListener(topic = "fancv", consumerGroup = "consumer-group")
@Component
public class ConsumerService implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        String topic = messageExt.getTopic();
        String tag = messageExt.getTags();
        String msg = new String(messageExt.getBody());
        System.out.println("*********************************");
        System.out.println("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + msg + ", tag:" + tag + ", topic:" + topic);
        System.out.println("*********************************");
    }
}
