package com.nb.mallchat.common.common.service.strategy.msg;

import com.nb.mallchat.common.common.dao.MessageDao;
import com.nb.mallchat.common.common.domain.entity.Message;
import com.nb.mallchat.common.common.domain.entity.MessageExtra;
import com.nb.mallchat.common.common.domain.enu.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Description:表情消息

 */
@Component
public class EmojisMsgHandler extends AbstractMsgHandler<EmojisMsgDTO> {
	@Autowired
	private MessageDao messageDao;

	@Override
	MessageTypeEnum getMsgTypeEnum() {
		return MessageTypeEnum.EMOJI;
	}

	@Override
	public void saveMsg(Message msg, EmojisMsgDTO body) {
		MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
		Message update = new Message();
		update.setId(msg.getId());
		update.setExtra(extra);
		extra.setEmojisMsgDTO(body);
		messageDao.updateById(update);
	}

	@Override
	public Object showMsg(Message msg) {
		return msg.getExtra().getEmojisMsgDTO();
	}

	@Override
	public Object showReplyMsg(Message msg) {
		return "表情";
	}

	@Override
	public String showContactMsg(Message msg) {
		return "[表情包]";
	}
}

