package com.nb.mallchat.common.common.service.strategy.msg;

import com.nb.mallchat.common.common.dao.MessageDao;
import com.nb.mallchat.common.common.domain.entity.Message;
import com.nb.mallchat.common.common.domain.entity.MessageExtra;
import com.nb.mallchat.common.common.domain.entity.msg.VideoMsgDTO;
import com.nb.mallchat.common.common.domain.enu.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Description:视频消息
 */
@Component
public class VideoMsgHandler extends AbstractMsgHandler<VideoMsgDTO> {
	@Autowired
	private MessageDao messageDao;

	@Override
	MessageTypeEnum getMsgTypeEnum() {
		return MessageTypeEnum.VIDEO;
	}

	@Override
	public void saveMsg(Message msg, VideoMsgDTO body) {
		MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
		Message update = new Message();
		update.setId(msg.getId());
		update.setExtra(extra);
		extra.setVideoMsgDTO(body);
		messageDao.updateById(update);
	}

	@Override
	public Object showMsg(Message msg) {
		return msg.getExtra().getVideoMsgDTO();
	}

	@Override
	public Object showReplyMsg(Message msg) {
		return "视频";
	}

	@Override
	public String showContactMsg(Message msg) {
		return "[视频]";
	}
}
