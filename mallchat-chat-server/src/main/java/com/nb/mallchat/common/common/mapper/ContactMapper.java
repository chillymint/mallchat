package com.nb.mallchat.common.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nb.mallchat.common.common.domain.entity.Contact;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会话列表 Mapper 接口
 * </p>
 */
public interface ContactMapper extends BaseMapper<Contact> {

	void refreshOrCreateActiveTime(@Param("roomId") Long roomId, @Param("memberUidList") List<Long> memberUidList, @Param("msgId") Long msgId, @Param("activeTime") Date activeTime);
}
