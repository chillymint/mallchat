package com.nb.mallchat.common.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.nb.mallchat.common.common.annotation.RedissionLock;
import com.nb.mallchat.common.common.dao.RoomFriendDao;
import com.nb.mallchat.common.common.domain.entity.RoomFriend;
import com.nb.mallchat.common.common.domain.vo.req.CursorPageBaseReq;
import com.nb.mallchat.common.common.domain.vo.req.PageBaseReq;
import com.nb.mallchat.common.common.domain.vo.resp.CursorPageBaseResp;
import com.nb.mallchat.common.common.domain.vo.resp.PageBaseResp;
import com.nb.mallchat.common.common.event.UserApplyEvent;
import com.nb.mallchat.common.common.service.ChatService;
import com.nb.mallchat.common.common.service.ContactService;
import com.nb.mallchat.common.common.service.RoomService;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.user.dao.UserApplyDao;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.dao.UserFriendDao;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.entity.UserApply;
import com.nb.mallchat.common.user.domain.entity.UserFriend;
import com.nb.mallchat.common.user.domain.vo.req.FriendApplyReq;
import com.nb.mallchat.common.user.domain.vo.req.FriendApproveReq;
import com.nb.mallchat.common.user.domain.vo.req.FriendCheckReq;
import com.nb.mallchat.common.user.domain.vo.resp.FriendApplyResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendCheckResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendUnreadResp;
import com.nb.mallchat.common.user.service.FriendService;
import com.nb.mallchat.common.user.service.adapter.FriendAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description : 好友
 */
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private UserFriendDao userFriendDao;
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	private RoomService roomService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoomFriendDao roomFriendDao;

	/**
	 * 检查
	 * 检查是否是自己好友
	 *
	 * @param uid     uid
	 * @param request 请求
	 */
	@Override
	public FriendCheckResp check(Long uid, FriendCheckReq request) {
		List<UserFriend> friendList = userFriendDao.getByFriends(uid, request.getUidList());

		Set<Long> friendUidSet = friendList.stream().map(UserFriend::getFriendUid).collect(Collectors.toSet());
		List<FriendCheckResp.FriendCheck> friendCheckList = request.getUidList().stream().map(friendUid -> {
			FriendCheckResp.FriendCheck friendCheck = new FriendCheckResp.FriendCheck();
			friendCheck.setUid(friendUid);
			friendCheck.setIsFriend(friendUidSet.contains(friendUid));
			return friendCheck;
		}).collect(Collectors.toList());
		return new FriendCheckResp(friendCheckList);
	}

	/**
	 * 申请好友
	 *
	 * @param request 请求
	 */
	@Override
	@RedissionLock(key = "#uid")
	public void apply(Long uid, FriendApplyReq request) {
		//是否有好友关系
		UserFriend friend = userFriendDao.getByFriend(uid, request.getTargetUid());
		AssertUtil.isEmpty(friend, "你们已经是好友了");
		//是否有待审批的申请记录(自己的)
		UserApply selfApproving = userApplyDao.getFriendApproving(uid, request.getTargetUid());
		if (Objects.nonNull(selfApproving)) {
			log.info("已有好友申请记录,uid:{}, targetId:{}", uid, request.getTargetUid());
			return;
		}
		//是否有待审批的申请记录(别人请求自己的)
		UserApply friendApproving = userApplyDao.getFriendApproving(request.getTargetUid(), uid);
		if (Objects.nonNull(friendApproving)) {
			((FriendService) AopContext.currentProxy()).applyApprove(uid, new FriendApproveReq(friendApproving.getId()));
			return;
		}
		//申请入库
		UserApply insert = FriendAdapter.buildFriendApply(uid, request);
		userApplyDao.save(insert);
		//申请事件
		applicationEventPublisher.publishEvent(new UserApplyEvent(this, insert));
	}

	/**
	 * 分页查询好友申请
	 *
	 * @param request 请求
	 */
	@Override
	public PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request) {
		IPage<UserApply> userApplyIPage = userApplyDao.friendApplyPage(uid, request.plusPage());
		if (CollectionUtil.isEmpty(userApplyIPage.getRecords())) {
			return PageBaseResp.empty();
		}
		//将这些申请列表设为已读
		readApples(uid, userApplyIPage);
		//返回消息
		return PageBaseResp.init(userApplyIPage, FriendAdapter.buildFriendApplyList(userApplyIPage.getRecords()));
	}

	private void readApples(Long uid, IPage<UserApply> userApplyIPage) {
		List<Long> applyIds = userApplyIPage.getRecords()
				.stream().map(UserApply::getId)
				.collect(Collectors.toList());
		userApplyDao.readApples(uid, applyIds);
	}

	/**
	 * 申请未读数
	 *
	 */
	@Override
	public FriendUnreadResp unread(Long uid) {
		Integer unReadCount = userApplyDao.getUnReadCount(uid);
		return new FriendUnreadResp(unReadCount);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@RedissionLock(key = "#uid")
	public void applyApprove(Long uid, FriendApproveReq request) {
		UserApply userApply = userApplyDao.getById(request.getApplyId());
		AssertUtil.isNotEmpty(userApply, "不存在申请记录");
		AssertUtil.equal(userApply.getTargetId(), uid, "不存在申请记录");
		AssertUtil.equal(userApply.getStatus(), 0, "已同意好友申请");
		//同意申请
		userApplyDao.agree(request.getApplyId());
		//创建双方好友关系
		createFriend(uid, userApply.getUid());
		//创建一个聊天房间
		RoomFriend roomFriend = roomService.createFriendRoom(Arrays.asList(uid, userApply.getUid()));
		//发送一条同意消息。。我们已经是好友了，开始聊天吧
		// chatService.sendMsg(MessageAdapter.buildAgreeMsg(roomFriend.getRoomId()), uid);
	}

	/**
	 * 删除好友
	 *
	 * @param uid       uid
	 * @param friendUid 朋友uid
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteFriend(Long uid, Long friendUid) {
		List<UserFriend> userFriends = userFriendDao.getUserFriend(uid, friendUid);
		if (CollectionUtil.isEmpty(userFriends)) {
			log.info("没有好友关系：{},{}", uid, friendUid);
			return;
		}
		List<Long> friendRecordIds = userFriends.stream().map(UserFriend::getId).collect(Collectors.toList());
		userFriendDao.removeByIds(friendRecordIds);
		//禁用房间
		roomService.disableFriendRoom(Arrays.asList(uid, friendUid));
	}

	@Override
	public CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request) {
		CursorPageBaseResp<UserFriend> friendPage = userFriendDao.getFriendPage(uid, request);
		if (CollectionUtils.isEmpty(friendPage.getList())) {
			return CursorPageBaseResp.empty();
		}
		List<Long> friendUids = friendPage.getList()
				.stream().map(UserFriend::getFriendUid)
				.collect(Collectors.toList());
		List<User> userList = userDao.getFriendList(friendUids);
		return CursorPageBaseResp.init(friendPage, FriendAdapter.buildFriend(friendPage.getList(), userList));
	}

	private void createFriend(Long uid, Long targetUid) {
		UserFriend userFriend1 = new UserFriend();
		userFriend1.setUid(uid);
		userFriend1.setFriendUid(targetUid);
		UserFriend userFriend2 = new UserFriend();
		userFriend2.setUid(targetUid);
		userFriend2.setFriendUid(uid);
		userFriendDao.saveBatch(Lists.newArrayList(userFriend1, userFriend2));
	}

}
