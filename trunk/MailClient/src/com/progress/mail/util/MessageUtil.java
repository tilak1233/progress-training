package com.progress.mail.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.progress.mail.client.msg.Message;
import com.progress.mail.impl.PropertiesReader;

/**
 * This class has the utility methods of message class
 * 
 * @author pchaitan
 * @since 29/12/2009
 */

public class MessageUtil {

	public static void showMessage(Message msgObj) {

		Print.printLine();
		Print.printString(PropertiesReader.getString("From")); //$NON-NLS-1$
		Print.printString(msgObj.getFromUser());
		Print.printLine();

		Print.printString(PropertiesReader.getString("To")); //$NON-NLS-1$
		List<String> toUsers = msgObj.getToUsers();
		for (String eachToUser : toUsers) {
			Print.printString(eachToUser);
			if (toUsers.indexOf(eachToUser) != toUsers.size() - 1)
				Print.printString(PropertiesReader.getString("Comma")); //$NON-NLS-1$
		}
		Print.printLine();

		Print.printString(PropertiesReader.getString("SentTime")); //$NON-NLS-1$
		Print.printTime(msgObj.getTimestamp());
		Print.printLine();

		Print.printString(PropertiesReader.getString("Subject")); //$NON-NLS-1$
		Print.printString(msgObj.getSubject());
		Print.printLine();

		Print.printString(PropertiesReader.getString("Msg")); //$NON-NLS-1$
		Print.printString(msgObj.getMsgBody());
		Print.printLine();
		Print.printLine();
	}

	public static void printMessageList(List<Message> msgList) {

		Print.printLine();
		Print.printString(PropertiesReader.getString("MsgNo")); //$NON-NLS-1$
		Print.printTab();
		Print.printString(PropertiesReader.getString("From")); //$NON-NLS-1$
		Print.printTab();
		Print.printString(PropertiesReader.getString("Subject")); //$NON-NLS-1$
		Print.printTab();
		Print.printString(PropertiesReader.getString("TimeStamp")); //$NON-NLS-1$
		Print.printLine();
		int noMsgs = msgList.size();
		for (int i = 0; i < noMsgs; i++) {
			Message msgObj = msgList.get(i);
			Print.printInteger(i + 1);
			Print.printTab();
			Print.printString(msgObj.getFromUser());
			Print.printTab();
			Print.printString(msgObj.getSubject());
			Print.printTab();
			Print.printTime(msgObj.getTimestamp());
			Print.printLine();
		}
		Print.printLine();
	}

	public static List<String> getToUsers() {

		try {

			List<String> toUsers = new ArrayList<String>();
			String users = ClientUtil.getInputFromConsole();
			String[] individualUsers = users.split(PropertiesReader
					.getString("Comma")); //$NON-NLS-1$
			for (int i = 0; i < individualUsers.length; i++) {
				toUsers.add(individualUsers[i]);
			}
			return toUsers;

		} catch (IOException ioe) {
			Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
			Print.printLine();
			return null;
		}
	}

	public static String getSubject() {

		try {
			String subject = ClientUtil.getInputFromConsole();
			return subject;
		} catch (IOException ioe) {
			Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
			Print.printLine();
			return null;
		}
	}

	public static String getMessage() {

		try {
			String message = null;
			StringBuffer fullMessage = new StringBuffer();
			while (!(message = ClientUtil.getInputFromConsole())
					.endsWith(Message.EOM)) {
				fullMessage.append(message
						+ PropertiesReader.getString("NewLine")); //$NON-NLS-1$
			}
			return (fullMessage.toString());
		} catch (IOException ioe) {
			Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
			Print.printLine();
			return null;
		}
	}

	public static List<Message> tempMsgList() {
		List<Message> msgList = new ArrayList<Message>();
		for (int i = 0; i < 5; i++) {
			Message msg = new Message();
			msg.setFromUser("phani"); //$NON-NLS-1$
			List<String> toUsers = new ArrayList<String>();
			toUsers.add("phani"); //$NON-NLS-1$
			msg.setToUsers(toUsers);
			msg.setSubject("subject"); //$NON-NLS-1$
			msg.setMsgBody("message body"); //$NON-NLS-1$
			msgList.add(msg);
		}
		return msgList;
	}
}
