package com.progress.mail;

import java.util.List;

import com.progress.mail.client.msg.Message;

/**
 * This interface needs to be extended by implementors of mail client handlers
 * 
 * @author stilak
 * 
 */
public interface MailHandler {
	
	public List<Message> getAllMails();

	public boolean isValidUser(String username, String password);
	
	public List<Message> getNewMails();
	
	public void sendMail(Message msg);

	public void logout();
	
}
