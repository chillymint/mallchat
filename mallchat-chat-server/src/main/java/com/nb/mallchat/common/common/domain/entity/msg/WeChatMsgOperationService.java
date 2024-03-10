package com.nb.mallchat.common.common.domain.entity.msg;

import java.util.List;

/**
 */
public interface WeChatMsgOperationService {
	/**
	 * 向被at的用户微信推送群聊消息
	 *
	 * @param senderUid senderUid
	 * @param receiverUidList receiverUidList
	 * @param msg msg
	 */
	void publishChatMsgToWeChatUser(long senderUid, List<Long> receiverUidList, String msg);
}
