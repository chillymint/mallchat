package com.nb.mallchat.common.common.utils;

// import com.nb.mallchat.transaction.annotation.SecureInvoke;
// import org.apache.rocketmq.spring.core.RocketMQTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.support.MessageBuilder;

// import com.mysql.cj.MessageBuilder;
// import com.nb.mallchat.common.common.annotation.SecureInvoke;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.connection.Message;
//

import com.nb.mallchat.common.common.annotation.SecureInvoke;
import org.springframework.beans.factory.annotation.Autowired;

/**发送mq工具类
 */
public class MQProducer {

	// @Autowired
	// private RocketMQTemplate rocketMQTemplate;

	public void sendMsg(String topic, Object body) {
		// Message<Object> build = MessageBuilder.withPayload(body).build();
		// rocketMQTemplate.send(topic, build);
	}

	/**
	 * 发送可靠消息，在事务提交后保证发送成功
	 *
	 * @param topic
	 * @param body
	 */
	@SecureInvoke
	public void sendSecureMsg(String topic, Object body, Object key) {
		// Message<Object> build = MessageBuilder
		// 		.withPayload(body)
		// 		.setHeader("KEYS", key)
		// 		.build();
		// rocketMQTemplate.send(topic, build);
	}
}