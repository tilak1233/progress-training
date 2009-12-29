package com.progress.mail.client.msg;

import java.sql.Timestamp;
import java.util.List;

/**
 * This class hold all the information for each mail message
 * @author stilak
 *
 */
public class Message {
	
	public static final String EOM = "<EOM>";

	private int msgId;
	
	private String fromUser;

	private List<String> toUsers;
	
	private String subject;
	
	private String msgBody;

	private Timestamp timestamp;
	
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

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getMsgId() {
		return msgId;
	}

}
