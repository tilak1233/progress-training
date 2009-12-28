package com.progress.mail.client.msg;

import java.util.List;

public class Message {

	private String fromUser;
	private List<String> toUsers;
	private String subject;
	private String msgBody;

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public List<String> getToUsers() {
		return toUsers;
	}

	public void setToUsers(List<String> toUsers) {
		this.toUsers = toUsers;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

}
