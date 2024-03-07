package com.nb.mallchat.common.common.service.strategy.mark;

import com.nb.mallchat.common.common.dao.MessageMarkDao;
import com.nb.mallchat.common.common.domain.dto.ChatMessageMarkDTO;
import com.nb.mallchat.common.common.domain.enu.MessageMarkActTypeEnum;
import com.nb.mallchat.common.common.domain.enu.MessageMarkTypeEnum;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMessageResp;
import com.nb.mallchat.common.common.event.MessageMarkEvent;
import com.nb.mallchat.common.common.exception.BusinessException;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

/**
 * Description: 消息标记抽象类
 */
public abstract class AbstractMsgMarkStrategy {
	@Autowired
	private MessageMarkDao messageMarkDao;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	protected abstract MessageMarkTypeEnum getTypeEnum();

	@Transactional
	public void mark(Long uid, Long msgId) {
		doMark(uid, msgId);
	}

	@Transactional
	public void unMark(Long uid, Long msgId) {
		doUnMark(uid, msgId);
	}

	@PostConstruct
	private void init() {
		MsgMarkFactory.register(getTypeEnum().getType(), this);
	}

	protected void doMark(Long uid, Long msgId) {
		exec(uid, msgId, MessageMarkActTypeEnum.MARK);
	}

	protected void doUnMark(Long uid, Long msgId) {
		exec(uid, msgId, MessageMarkActTypeEnum.UN_MARK);
	}

	protected void exec(Long uid, Long msgId, MessageMarkActTypeEnum actTypeEnum) {
		Integer markType = getTypeEnum().getType();
		Integer actType = actTypeEnum.getType();
		ChatMessageResp.MessageMark oldMark = messageMarkDao.get(uid, msgId, markType);
		if (Objects.isNull(oldMark) && actTypeEnum == MessageMarkActTypeEnum.UN_MARK) {
			//取消的类型，数据库一定有记录，没有就直接跳过操作
			return;
		}
		//插入一条新消息,或者修改一条消息
		ChatMessageResp.MessageMark insertOrUpdate = ChatMessageResp.MessageMark.builder()
				.id(Optional.ofNullable(oldMark).map(ChatMessageResp.MessageMark::getId).orElse(null))
				.uid(uid)
				.msgId(msgId)
				.type(markType)
				.status(transformAct(actType))
				.build();
		boolean modify = messageMarkDao.saveOrUpdate(insertOrUpdate);
		if (modify) {
			//修改成功才发布消息标记事件
			ChatMessageMarkDTO dto = new ChatMessageMarkDTO(uid, msgId, markType, actType);
			applicationEventPublisher.publishEvent(new MessageMarkEvent(this, dto));
		}
	}

	private Integer transformAct(Integer actType) {
		if (actType == 1) {
			return YesOrNoEnum.NO.getStatus();
		} else if (actType == 2) {
			return YesOrNoEnum.YES.getStatus();
		}
		throw new BusinessException("动作类型 1确认 2取消");
	}

}
