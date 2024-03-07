package com.nb.mallchat.common.user.service.cache;

import com.nb.mallchat.common.common.constant.RedisKey;
import com.nb.mallchat.common.common.dao.RoomFriendDao;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description: 房间基本信息的缓存
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-06-10
 */
@Component
public class RoomCache extends AbstractRedisStringCache<Long, Room> {
	@Autowired
	private UserDao userDao;
	// @Autowired
	// private RoomDao roomDao;
	@Autowired
	private RoomFriendDao roomFriendDao;

	@Override
	protected String getKey(Long roomId) {
		return RedisKey.getKey(RedisKey.ROOM_INFO_STRING, roomId);
	}

	@Override
	protected Long getExpireSeconds() {
		return 5 * 60L;
	}

	@Override
	protected Map<Long, Room> load(List<Long> req) {
		return null;
	}

	// @Override
	// protected Map<Long, Room> load(List<Long> roomIds) {
	// 	List<Room> rooms = roomDao.listByIds(roomIds);
	// 	return rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
	// }
}
