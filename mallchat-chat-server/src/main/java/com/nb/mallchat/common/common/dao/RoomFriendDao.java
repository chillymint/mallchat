package com.nb.mallchat.common.common.dao;

/**
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nb.mallchat.common.common.domain.entity.RoomFriend;
import com.nb.mallchat.common.common.domain.enu.NormalOrNoEnum;
import com.nb.mallchat.common.common.mapper.RoomFriendMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 单聊房间表 服务实现类
 * </p>
 *
 */
@Service
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {

	public RoomFriend getByKey(String key) {
		return lambdaQuery().eq(RoomFriend::getRoomKey, key).one();
	}

	public void restoreRoom(Long id) {
		lambdaUpdate()
				.eq(RoomFriend::getId, id)
				.set(RoomFriend::getStatus, NormalOrNoEnum.NORMAL.getStatus())
				.update();
	}

	public void disableRoom(String key) {
		lambdaUpdate()
				.eq(RoomFriend::getRoomKey, key)
				.set(RoomFriend::getStatus, NormalOrNoEnum.NOT_NORMAL.getStatus())
				.update();
	}

	public List<RoomFriend> listByRoomIds(List<Long> roomIds) {
		return lambdaQuery()
				.in(RoomFriend::getRoomId, roomIds)
				.list();
	}

	public RoomFriend getByRoomId(Long roomID) {
		return lambdaQuery()
				.eq(RoomFriend::getRoomId, roomID)
				.one();
	}
}
