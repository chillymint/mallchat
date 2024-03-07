package com.nb.mallchat.common.user.service;

import com.nb.mallchat.common.user.domain.dto.ItemInfoDTO;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.vo.req.BlackReq;
import com.nb.mallchat.common.user.domain.vo.req.ItemInfoReq;
import com.nb.mallchat.common.user.domain.vo.resp.BadgeResp;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService {

    Long register(User insert);

	UserInfoResp getUserInfo(Long uid);

	void modifyName(Long uid, String name);

	List<BadgeResp> badges(Long uid);

	void wearingBadge(Long uid, Long badgeId);

    void black(BlackReq req);

	List<ItemInfoDTO> getItemInfo(ItemInfoReq req);
}
