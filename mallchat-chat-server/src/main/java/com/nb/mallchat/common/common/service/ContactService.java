package com.nb.mallchat.common.common.service;


import com.nb.mallchat.common.common.domain.dto.MsgReadInfoDTO;
import com.nb.mallchat.common.common.domain.entity.Contact;
import com.nb.mallchat.common.common.domain.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会话列表 服务类
 * </p>
 */
public interface ContactService {
	/**
	 * 创建会话
	 */
	Contact createContact(Long uid, Long roomId);

	Integer getMsgReadCount(Message message);

	Integer getMsgUnReadCount(Message message);

	Map<Long, MsgReadInfoDTO> getMsgReadInfo(List<Message> messages);
}
