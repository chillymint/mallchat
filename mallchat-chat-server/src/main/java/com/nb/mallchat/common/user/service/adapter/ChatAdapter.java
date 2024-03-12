package com.nb.mallchat.common.user.service.adapter;

import com.nb.mallchat.common.common.domain.entity.Contact;
import com.nb.mallchat.common.common.domain.entity.RoomFriend;
import com.nb.mallchat.common.common.domain.entity.RoomGroup;
import com.nb.mallchat.common.common.domain.enu.HotFlagEnum;
import com.nb.mallchat.common.common.domain.enu.NormalOrNoEnum;
import com.nb.mallchat.common.common.domain.enu.RoomTypeEnum;
import com.nb.mallchat.common.user.domain.entity.Room;
import com.nb.mallchat.common.user.domain.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 */
public class ChatAdapter {
	public static final String SEPARATOR = ",";

	public static String generateRoomKey(List<Long> uidList) {
		return uidList.stream()
				.sorted()
				.map(String::valueOf)
				.collect(Collectors.joining(SEPARATOR));
	}

	public static Room buildRoom(RoomTypeEnum typeEnum) {
		Room room = new Room();
		room.setType(typeEnum.getType());
		room.setHotFlag(HotFlagEnum.NOT.getType());
		return room;
	}

	public static RoomFriend buildFriendRoom(Long roomId, List<Long> uidList) {
		List<Long> collect = uidList.stream().sorted().collect(Collectors.toList());
		RoomFriend roomFriend = new RoomFriend();
		roomFriend.setRoomId(roomId);
		roomFriend.setUid1(collect.get(0));
		roomFriend.setUid2(collect.get(1));
		roomFriend.setRoomKey(generateRoomKey(uidList));
		roomFriend.setStatus(NormalOrNoEnum.NORMAL.getStatus());
		return roomFriend;
	}

	public static Contact buildContact(Long uid, Long roomId) {
		Contact contact = new Contact();
		contact.setRoomId(roomId);
		contact.setUid(uid);
		return contact;
	}

	public static Set<Long> getFriendUidSet(Collection<RoomFriend> values, Long uid) {
		return values.stream()
				.map(a -> getFriendUid(a, uid))
				.collect(Collectors.toSet());
	}

	/**
	 * 获取好友uid
	 */
	public static Long getFriendUid(RoomFriend roomFriend, Long uid) {
		return Objects.equals(uid, roomFriend.getUid1()) ? roomFriend.getUid2() : roomFriend.getUid1();
	}

	public static RoomGroup buildGroupRoom(User user, Long roomId) {
		RoomGroup roomGroup = new RoomGroup();
		roomGroup.setName(user.getName() + "的群组");
		roomGroup.setAvatar(user.getAvatar());
		roomGroup.setRoomId(roomId);
		return roomGroup;
	}
}
