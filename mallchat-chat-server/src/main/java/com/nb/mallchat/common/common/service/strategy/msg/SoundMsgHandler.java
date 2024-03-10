package com.nb.mallchat.common.common.service.strategy.msg;

import com.nb.mallchat.common.common.dao.MessageDao;
import com.nb.mallchat.common.common.domain.entity.Message;
import com.nb.mallchat.common.common.domain.entity.MessageExtra;
import com.nb.mallchat.common.common.domain.entity.msg.SoundMsgDTO;
import com.nb.mallchat.common.common.domain.enu.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Description:语音消息
 */
@Component
public class SoundMsgHandler extends AbstractMsgHandler<SoundMsgDTO> {
	@Autowired
	private MessageDao messageDao;

	@Override
	MessageTypeEnum getMsgTypeEnum() {
		return MessageTypeEnum.SOUND;
	}

	@Override
	public void saveMsg(Message msg, SoundMsgDTO body) {
		MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
		Message update = new Message();
		update.setId(msg.getId());
		update.setExtra(extra);
		extra.setSoundMsgDTO(body);
		messageDao.updateById(update);
	}

	@Override
	public Object showMsg(Message msg) {
		return msg.getExtra().getSoundMsgDTO();
	}

	@Override
	public Object showReplyMsg(Message msg) {
		return "语音";
	}

	@Override
	public String showContactMsg(Message msg) {
		return "[语音]";
	}
}
