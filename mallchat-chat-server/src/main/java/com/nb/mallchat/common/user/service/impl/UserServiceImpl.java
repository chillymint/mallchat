package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.annotation.RedissionLock;
import com.nb.mallchat.common.common.event.UserRegisterEvent;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.user.dao.ItemConfigDao;
import com.nb.mallchat.common.user.dao.UserBackpackDao;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.dto.Black;
import com.nb.mallchat.common.user.domain.dto.ItemInfoDTO;
import com.nb.mallchat.common.user.domain.entity.ItemConfig;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.BlackTypeEnum;
import com.nb.mallchat.common.user.domain.enums.ItemEnum;
import com.nb.mallchat.common.user.domain.enums.ItemTypeEnum;
import com.nb.mallchat.common.user.domain.vo.req.BlackReq;
import com.nb.mallchat.common.user.domain.vo.req.ItemInfoReq;
import com.nb.mallchat.common.user.domain.vo.resp.BadgeResp;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.nb.mallchat.common.user.service.UserService;
import com.nb.mallchat.common.user.service.adapter.UserAdapter;
import com.nb.mallchat.common.user.service.cache.ItemCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ItemConfigDao itemConfigDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        //todo 用户注册
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, insert));
        return insert.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissionLock(key = "#uid")
    public void modifyName(Long uid, String name) {
        //事务锁
        User oldUser = userDao.getByName(name);
        AssertUtil.isEmpty(oldUser, "名称重复请更换！");
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameItem, "改名卡不够了,请等待后续活动赠送!");
        // 使用改名卡
        boolean success = userBackpackDao.useItem(modifyNameItem);
        if (success) {
            // 改名
            userDao.modifyName(uid, name);
        }
    }

    @Override
    public List<BadgeResp> badges(Long uid) {
        //查询所有徽章
        List<ItemConfig> itemConfigs = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        //查询用户拥有徽章
        List<UserBackpack> backpacks = userBackpackDao.getByItemIds(uid, itemConfigs.stream()
                .map(ItemConfig::getId).collect(Collectors.toList()));
        //查询用户佩戴的徽章
        User user = userDao.getById(uid);

        return UserAdapter.buildBadgeResp(itemConfigs, backpacks, user);
    }

    @Override
    public void wearingBadge(Long uid, Long itemId) {
        // 确保有徽章
        UserBackpack firstValidItem = userBackpackDao.getFirstValidItem(uid, itemId);

        AssertUtil.isNotEmpty(firstValidItem, "您还没有获得这个徽章，快去获得吧!");
        // 确保这个物品是徽章
        ItemConfig itemConfig = itemConfigDao.getById(firstValidItem.getItemId());
        AssertUtil.equal(itemConfig.getType(), ItemTypeEnum.BADGE.getType(), "只有徽章才能佩戴");
        userDao.wearingBadge(uid, itemId);
    }

    @Override
    public void black(BlackReq req) {
        Long uid = req.getUid();
        Black user = new Black();
        user.setType(BlackTypeEnum.UID.getType());
        user.setTarget(uid.toString());
//        blackDao.save(user);
//        User byId = userDao.getById(id);
//        blackIp(byId.getIpInfo().getCreateIp());
//        blackIp(byId.getIpInfo().getUpdateIp());
    }

    // 前后端异步懒加载
    @Override
    public List<ItemInfoDTO> getItemInfo(ItemInfoReq req) {//简单做，更新时间可判断被修改
        return req.getReqList().stream().map(a -> {
            ItemConfig itemConfig = itemCache.getById(a.getItemId());
            if (Objects.nonNull(a.getLastModifyTime()) && a.getLastModifyTime() >= itemConfig.getUpdateTime().getTime()) {
                return ItemInfoDTO.skip(a.getItemId());
            }
            ItemInfoDTO dto = new ItemInfoDTO();
            dto.setItemId(itemConfig.getId());
            dto.setImg(itemConfig.getImg());
            dto.setDescribe(itemConfig.getDescribe());
            return dto;
        }).collect(Collectors.toList());
    }
}
