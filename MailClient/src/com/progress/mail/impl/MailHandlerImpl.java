package com.progress.mail.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.progress.mail.MailHandler;
import com.progress.mail.client.msg.Message;
import com.progress.mail.util.DBUtil;

/**
 * This class implements MailHandler interface to provide mail client
 * functionality by using the MySQL Database as server
 * 
 * @author stilak
 */

public class MailHandlerImpl implements MailHandler{
	
	private Connection conn = null;
	private String username;

	public Connection getConnection() {
		return conn;
	}

	public List<Message> getAllMails() {
		String query = "select * from mailbox serverMailBox where serverMailBox.msg_id in (select msg_id from mailbox_"+ username+")"; //$NON-NLS-1$
		return getMessagesFromDB(query);
	}

	private List<Message> getMessagesFromDB(String query) {
		List<Message> messagesFromDB = new ArrayList<Message>();
		try {
			messagesFromDB = DBUtil.getMessagesFromDB(query, conn);
			String updateQuery = "update clients set lastdisplay= current_timestamp where email_id = '" + username + "'"; //$NON-NLS-1$ //$NON-NLS-2$
			DBUtil.executeUpdateQuery(updateQuery, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messagesFromDB;
	}

	public boolean isValidUser(String username, String password) {
		this.username = username;
		try {
			conn = DBUtil.createConnection(username, password);
		} catch (SQLException e) {
			return false;
		}

		String updateQuery = "update clients set lastlogin = current_timestamp where email_id = '" + username + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			DBUtil.executeUpdateQuery(updateQuery, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<Message> getNewMails() {
		String query = "select * from mailbox serverMailBox where serverMailBox.msg_id in (select msg_id from mailbox_"+ username+") and serverMailBox.msg_time >= (select lastdisplay from clients where email_id='" //$NON-NLS-1$
				+ username + "')"; //$NON-NLS-1$

		return getMessagesFromDB(query);
	}

	public boolean sendMail(Message msg) {
		try {
			String query = "call SP_UPDATE_MAILBOXES( ?, ?, ?, ?)"; //$NON-NLS-1$
			PreparedStatement pstmt = conn.prepareStatement(query);
			String toUsers = getToUsers(msg.getToUsers());
			if( toUsers.length() == 0)
				return false;
			pstmt.setString(1, toUsers);
			pstmt.setString(2, msg.getFromUser());
			pstmt.setString(3, msg.getSubject());
			pstmt.setString(4, msg.getMsgBody());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String getToUsers(List<String> toUsers) {
		StringBuffer users = new StringBuffer();
		for (String user : toUsers) {
			if( user != null && user.trim().length() != 0 )
				users.append(user);
		}
		return users.toString();
	}

	public void logout(){
		String updateQuery = "update clients set lastlogout= current_timestamp where email_id = '" + username + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			DBUtil.executeUpdateQuery(updateQuery, conn);
			conn.close();
			System.out.println(username+ " logged out!"); //$NON-NLS-1$
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MailHandlerImpl mailHandler = new MailHandlerImpl();
		boolean isValidUser = mailHandler.isValidUser("stilak", "stilak"); //$NON-NLS-1$ //$NON-NLS-2$
		if( ! isValidUser )
			return;
		List<Message> allMails = mailHandler.getAllMails();
		for (Message msg : allMails) {
			System.out.println("Msg Id: " + msg.getMsgId()); //$NON-NLS-1$
			System.out.println("From : " + msg.getFromUser()); //$NON-NLS-1$
			System.out.println("To: "+ msg.getToUsers()); //$NON-NLS-1$
			System.out.println("Subject: "+ msg.getSubject()); //$NON-NLS-1$
			System.out.println("Message:"+ msg.getMsgBody()); //$NON-NLS-1$
		}
		Message msg = new Message();
		ArrayList<String> toUsers = new ArrayList<String>();
		toUsers.add("cmittappa"); //$NON-NLS-1$
		msg.setToUsers(toUsers);
		msg.setFromUser("stilak"); //$NON-NLS-1$
		msg.setSubject("Hi"); //$NON-NLS-1$
		msg.setMsgBody("hello this is a test message"); //$NON-NLS-1$
		mailHandler.sendMail(msg);
		mailHandler.logout();
	}

}
