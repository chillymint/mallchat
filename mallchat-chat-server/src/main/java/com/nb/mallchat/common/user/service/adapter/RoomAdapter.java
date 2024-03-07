package com.nb.mallchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.nb.mallchat.common.common.domain.entity.Contact;
import com.nb.mallchat.common.common.domain.entity.GroupMember;
import com.nb.mallchat.common.common.domain.entity.RoomGroup;
import com.nb.mallchat.common.common.domain.enu.GroupRoleEnum;
import com.nb.mallchat.common.common.domain.enu.MessageTypeEnum;
import com.nb.mallchat.common.common.domain.vo.req.ChatMessageReq;
import com.nb.mallchat.common.common.domain.vo.resp.ChatMessageReadResp;
import com.nb.mallchat.common.common.domain.vo.resp.ChatRoomResp;
import com.nb.mallchat.common.user.domain.entity.Room;
import com.nb.mallchat.common.user.domain.entity.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 消息适配器
 */
public class RoomAdapter {


	public static List<ChatRoomResp> buildResp(List<Room> list) {
		return list.stream()
				.map(a -> {
					ChatRoomResp resp = new ChatRoomResp();
					BeanUtil.copyProperties(a, resp);
					resp.setActiveTime(a.getActiveTime());
					return resp;
				}).collect(Collectors.toList());
	}

	public static List<ChatMessageReadResp> buildReadResp(List<Contact> list) {
		return list.stream().map(contact -> {
			ChatMessageReadResp resp = new ChatMessageReadResp();
			resp.setUid(contact.getUid());
			return resp;
		}).collect(Collectors.toList());
	}

	public static List<GroupMember> buildGroupMemberBatch(List<Long> uidList, Long groupId) {
		return uidList.stream()
				.distinct()
				.map(uid -> {
					GroupMember member = new GroupMember();
					member.setRole(GroupRoleEnum.MEMBER.getType());
					member.setUid(uid);
					member.setGroupId(groupId);
					return member;
				}).collect(Collectors.toList());
	}

	public static ChatMessageReq buildGroupAddMessage(RoomGroup groupRoom, User inviter, Map<Long, User> member) {
		ChatMessageReq chatMessageReq = new ChatMessageReq();
		chatMessageReq.setRoomId(groupRoom.getRoomId());
		chatMessageReq.setMsgType(MessageTypeEnum.SYSTEM.getType());
		StringBuilder sb = new StringBuilder();
		sb.append("\"")
				.append(inviter.getName())
				.append("\"")
				.append("邀请")
				.append(member.values().stream().map(u -> "\"" + u.getName() + "\"").collect(Collectors.joining(",")))
				.append("加入群聊");
		chatMessageReq.setBody(sb.toString());
		return chatMessageReq;
	}
}
