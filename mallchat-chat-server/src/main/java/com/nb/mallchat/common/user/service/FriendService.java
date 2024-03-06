package com.nb.mallchat.common.user.service;

import com.nb.mallchat.common.common.domain.vo.req.CursorPageBaseReq;
import com.nb.mallchat.common.common.domain.vo.req.PageBaseReq;
import com.nb.mallchat.common.common.domain.vo.resp.CursorPageBaseResp;
import com.nb.mallchat.common.common.domain.vo.resp.PageBaseResp;
import com.nb.mallchat.common.user.domain.vo.req.FriendApplyReq;
import com.nb.mallchat.common.user.domain.vo.req.FriendApproveReq;
import com.nb.mallchat.common.user.domain.vo.req.FriendCheckReq;
import com.nb.mallchat.common.user.domain.vo.resp.FriendApplyResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendCheckResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendResp;
import com.nb.mallchat.common.user.domain.vo.resp.FriendUnreadResp;

/**
 *  联系人整体功能实现
 */
public interface FriendService {

	/**
	 * 检查
	 * 检查是否是自己好友
	 *
	 * @param request 请求
	 * @param uid     uid
	 * @return {@link FriendCheckResp}
	 */
	FriendCheckResp check(Long uid, FriendCheckReq request);

	/**
	 * 应用
	 * 申请好友
	 *
	 * @param request 请求
	 * @param uid     uid
	 */
	void apply(Long uid, FriendApplyReq request);

	/**
	 * 分页查询好友申请
	 *
	 * @param request 请求
	 // * @return {@link PageBaseResp}<{@link FriendApplyResp}>
	 */
	PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request);

	/**
	 * 申请未读数
	 *
	 */
	FriendUnreadResp unread(Long uid);

	/**
	 * 同意好友申请
	 *
	 * @param uid     uid
	 * @param request 请求
	 */
	void applyApprove(Long uid, FriendApproveReq request);

	/**
	 * 删除好友
	 *
	 * @param uid       uid
	 * @param friendUid 朋友uid
	 */
	void deleteFriend(Long uid, Long friendUid);

	CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request);
}
