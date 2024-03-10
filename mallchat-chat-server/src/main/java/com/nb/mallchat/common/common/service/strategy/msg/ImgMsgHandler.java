package com.nb.mallchat.common.common.service.strategy.msg;

import com.nb.mallchat.common.common.dao.MessageDao;
import com.nb.mallchat.common.common.domain.entity.Message;
import com.nb.mallchat.common.common.domain.entity.MessageExtra;
import com.nb.mallchat.common.common.domain.entity.msg.ImgMsgDTO;
import com.nb.mallchat.common.common.domain.enu.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 */
@Component
public class ImgMsgHandler extends AbstractMsgHandler<ImgMsgDTO> {
	@Autowired
	private MessageDao messageDao;

	@Override
	MessageTypeEnum getMsgTypeEnum() {
		return MessageTypeEnum.IMG;
	}

	@Override
	public void saveMsg(Message msg, ImgMsgDTO body) {
		MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
		Message update = new Message();
		update.setId(msg.getId());
		update.setExtra(extra);
		extra.setImgMsgDTO(body);
		messageDao.updateById(update);
	}

	@Override
	public Object showMsg(Message msg) {
		return msg.getExtra().getImgMsgDTO();
	}

	@Override
	public Object showReplyMsg(Message msg) {
		return "图片";
	}

	@Override
	public String showContactMsg(Message msg) {
		return "[图片]";
	}
}
