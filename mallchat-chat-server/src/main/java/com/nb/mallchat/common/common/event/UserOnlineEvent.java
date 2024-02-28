package com.nb.mallchat.common.common.event;

import com.nb.mallchat.common.user.domain.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 */
public class UserOnlineEvent extends ApplicationEvent {
	private User user;
	public UserOnlineEvent(Object source, User user) {
		super(source);
		this.user = user;
	}
}
