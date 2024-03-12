package com.nb.mallchat.common.common.service;

import com.nb.mallchat.common.common.domain.vo.member.MemberReq;
import com.nb.mallchat.common.common.domain.vo.req.*;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMemberListResp;
import com.nb.mallchat.common.common.domain.vo.resp.ChatRoomResp;
import com.nb.mallchat.common.common.domain.vo.resp.CursorPageBaseResp;
import com.nb.mallchat.common.common.domain.vo.resp.MemberResp;
import com.nb.mallchat.common.websocket.domain.vo.resp.ChatMemberResp;

import java.util.List;

/**
 * Description:
 */
public interface RoomAppService {
	/**
	 * 获取会话列表--支持未登录态
	 */
	CursorPageBaseResp<ChatRoomResp> getContactPage(CursorPageBaseReq request, Long uid);

	/**
	 * 获取群组信息
	 */
	MemberResp getGroupDetail(Long uid, long roomId);

	CursorPageBaseResp<ChatMemberResp> getMemberPage(MemberReq request);

	List<ChatMemberListResp> getMemberList(ChatMessageMemberReq request);

	void delMember(Long uid, MemberDelReq request);

	void addMember(Long uid, MemberAddReq request);

	Long addGroup(Long uid, GroupAddReq request);

	ChatRoomResp getContactDetail(Long uid, Long roomId);

	ChatRoomResp getContactDetailByFriend(Long uid, Long friendUid);
}
