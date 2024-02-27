package com.nb.mallchat.common.common.event;

import com.nb.mallchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {
	private final User user;
	public UserRegisterEvent(Object source, User user) {
		super(source);
		this.user = user;
	}

}
