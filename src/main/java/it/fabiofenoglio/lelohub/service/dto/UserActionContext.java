package it.fabiofenoglio.lelohub.service.dto;

import java.time.ZonedDateTime;

import it.fabiofenoglio.lelohub.domain.User;

public class UserActionContext {

	private ZonedDateTime timestamp;
	private String userLogin;
	private User user;

	public UserActionContext(User user) {
		super();
		this.timestamp = ZonedDateTime.now();
		this.userLogin = user != null ? user.getLogin() : null;
		this.user = user;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
