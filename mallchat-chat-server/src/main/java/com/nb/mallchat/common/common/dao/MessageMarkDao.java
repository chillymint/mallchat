package com.nb.mallchat.common.common.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nb.mallchat.common.common.domain.enu.NormalOrNoEnum;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMessageResp;
import com.nb.mallchat.common.common.mapper.MessageMarkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息标记表 服务实现类
 * </p>
 *
 */
@Service
public class MessageMarkDao extends ServiceImpl<MessageMarkMapper, ChatMessageResp.MessageMark> {

	public ChatMessageResp.MessageMark get(Long uid, Long msgId, Integer markType) {
		return lambdaQuery().eq(ChatMessageResp.MessageMark::getUid, uid)
				.eq(ChatMessageResp.MessageMark::getMsgId, msgId)
				.eq(ChatMessageResp.MessageMark::getType, markType)
				.one();
	}

	public Integer getMarkCount(Long msgId, Integer markType) {
		return lambdaQuery().eq(ChatMessageResp.MessageMark::getMsgId, msgId)
				.eq(ChatMessageResp.MessageMark::getType, markType)
				.eq(ChatMessageResp.MessageMark::getStatus, NormalOrNoEnum.NORMAL.getStatus())
				.count();
	}

	public List<ChatMessageResp.MessageMark> getValidMarkByMsgIdBatch(List<Long> msgIds) {
		return lambdaQuery()
				.in(ChatMessageResp.MessageMark::getMsgId, msgIds)
				.eq(ChatMessageResp.MessageMark::getStatus, NormalOrNoEnum.NORMAL.getStatus())
				.list();
	}
}
