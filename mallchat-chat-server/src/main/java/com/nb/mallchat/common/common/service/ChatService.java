package com.nb.mallchat.common.common.service;

import com.nb.mallchat.common.common.domain.dto.MsgReadInfoDTO;
import com.nb.mallchat.common.common.domain.entity.Message;
import com.nb.mallchat.common.common.domain.vo.member.MemberReq;
import com.nb.mallchat.common.common.domain.vo.req.*;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMessageReadResp;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMessageResp;
import com.nb.mallchat.common.common.domain.vo.resp.CursorPageBaseResp;
import com.nb.mallchat.common.websocket.domain.vo.resp.ChatMemberResp;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Description: 消息处理类
 */
public interface ChatService {
	//
	// /**
	//  * 发送消息
	//  *
	//  * @param request
	//  */
	Long sendMsg(ChatMessageReq request, Long uid);
	//
	// /**
	//  * 根据消息获取消息前端展示的物料
	//  *
	//  * @param message
	//  * @param receiveUid 接受消息的uid，可null
	//  * @return
	//  */
	ChatMessageResp getMsgResp(Message message, Long receiveUid);
	//
	// /**
	//  * 根据消息获取消息前端展示的物料
	//  *
	//  * @param msgId
	//  * @param receiveUid 接受消息的uid，可null
	//  * @return
	//  */
	ChatMessageResp getMsgResp(Long msgId, Long receiveUid);

	/**
	 * 获取群成员列表
	 *
	 * @param memberUidList
	 * @param request
	 * @return
	 */
	CursorPageBaseResp<ChatMemberResp> getMemberPage(List<Long> memberUidList, MemberReq request);

	/**
	 * 获取消息列表
	 *
	 * @param request
	 * @return
	 */
	CursorPageBaseResp<ChatMessageResp> getMsgPage(ChatMessagePageReq request, @Nullable Long receiveUid);
	//
	// ChatMemberStatisticResp getMemberStatistic();
	//
	void setMsgMark(Long uid, ChatMessageMarkReq request);
	//
	void recallMsg(Long uid, ChatMessageBaseReq request);
	//
	// List<ChatMemberListResp> getMemberList(ChatMessageMemberReq chatMessageMemberReq);
	//
	Collection<MsgReadInfoDTO> getMsgReadInfo(Long uid, ChatMessageReadInfoReq request);
	//
	CursorPageBaseResp<ChatMessageReadResp> getReadPage(Long uid, ChatMessageReadReq request);
	//
	void msgRead(Long uid, ChatMessageMemberReq request);
}
