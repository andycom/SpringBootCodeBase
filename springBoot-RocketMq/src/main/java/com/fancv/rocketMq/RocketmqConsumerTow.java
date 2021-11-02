package com.fancv.rocketMq;

import com.fancv.service.DemoService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "${rocketmq.topic}", consumerGroup = "${rocketmq.consumer.group_b}",
        selectorExpression = "${rocketmq.tags_b}")
public class RocketmqConsumerTow implements RocketMQListener<MessageExt> {
    Logger logger = LoggerFactory.getLogger(RocketmqConsumerTow.class);

    @Autowired
    DemoService demoService;

    @Override
    public void onMessage(MessageExt message) {
        String msgBody = new String(message.getBody());
        String serviceName = message.getTags();
        logger.info("本次消费服务名称：tags_b {}", msgBody);
        demoService.getStart(serviceName);

    }
}


