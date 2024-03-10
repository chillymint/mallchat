package com.nb.mallchat.common.common.service;

import com.nb.mallchat.common.common.constant.MQConstant;
import com.nb.mallchat.common.common.domain.dto.PushMessageDTO;
import com.nb.mallchat.common.common.utils.MQProducer;
import com.nb.mallchat.common.websocket.domain.vo.resp.WSBaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushService {
	@Autowired
	private MQProducer mqProducer;

	public void sendPushMsg(WSBaseResp<?> msg, List<Long> uidList) {
		mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(uidList, msg));
	}

	public void sendPushMsg(WSBaseResp<?> msg, Long uid) {
		mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(uid, msg));
	}

	public void sendPushMsg(WSBaseResp<?> msg) {
		mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(msg));
	}
}
