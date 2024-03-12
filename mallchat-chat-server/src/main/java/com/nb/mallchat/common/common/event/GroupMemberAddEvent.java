package com.nb.mallchat.common.common.event;

import com.nb.mallchat.common.common.domain.entity.GroupMember;
import com.nb.mallchat.common.common.domain.entity.RoomGroup;
import com.nb.mallchat.common.common.service.impl.RoomAppServiceImpl;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;


@Getter
public class GroupMemberAddEvent extends ApplicationEvent {

	private final List<GroupMember> memberList;
	private final RoomGroup roomGroup;
	private final Long inviteUid;

	public GroupMemberAddEvent(Object source, RoomGroup roomGroup, List<GroupMember> memberList, Long inviteUid) {
		super(source);
		this.memberList = memberList;
		this.roomGroup = roomGroup;
		this.inviteUid = inviteUid;
	}

}
