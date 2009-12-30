package com.progress.mail.client.msg;


import java.io.IOException;
import java.util.List;

import com.progress.mail.MailHandler;
import com.progress.mail.impl.MailHandlerImpl;
import com.progress.mail.impl.PropertiesReader;
import com.progress.mail.util.ClientUtil;
import com.progress.mail.util.MessageUtil;
import com.progress.mail.util.Print;

/**
 * This class depicts the mail client functionality.
 * @author pchaitan
 * @since 28/12/2009
 */

public class Client {

	private String emailID;

	private String password;

	private MailHandler mailHandler;

	public Client() {
		mailHandler = new MailHandlerImpl();
	}

	private void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	private String getEmailID() {
		return emailID;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	private String getPassword() {
		return password;
	}

	public void startClient() {

		int count = 0;
		boolean isLogin = false;

		isLogin = login();
		count++;
		while (isLogin == false && count < 3) {
			Print.printString(PropertiesReader.getString("LoginErr")); //$NON-NLS-1$
			Print.printLine();
			Print.printLine();
			isLogin = login();
			count++;
		}
		if (isLogin == false) {
			Print.printString(PropertiesReader
					.getString("LoginTrialsExceededErr")); //$NON-NLS-1$
			System.exit(0);
		} else {
			Print.printString(PropertiesReader.getString("LoginSuccess")); //$NON-NLS-1$
			Print.printLine();
			processCommands();
		}
	}

	private void processCommands() {

		int choice = 0;
		String input = ""; //$NON-NLS-1$

		while (choice != 4) {
			ClientUtil.printMailMenu();
			try {
				input = ClientUtil.getInputFromConsole();
				choice = Integer.parseInt(input);
				executeCommand(choice);
			} catch (IOException ioe) {
				Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
				Print.printLine();
			}
		}
	}

	private void executeCommand(int choice) {
		switch (choice) {
		case 1:
			showAll();
			break;
		case 2:
			display();
			break;
		case 3:
			compose();
			break;
		case 4:
			logout();
			break;
		}
	}

	private boolean login() {

		try {

			Print.printString(PropertiesReader.getString("EnterUsername")); //$NON-NLS-1$
			String username = ClientUtil.getInputFromConsole();
			setEmailID(username);
			Print.printLine();
			Print.printString(PropertiesReader.getString("EnterPassword")); //$NON-NLS-1$
			String password = ClientUtil.getInputFromConsole();
			setPassword(password);
			Print.printLine();
			return (mailHandler.isValidUser(getEmailID(), getPassword()));

		} catch (IOException ioe) {
			Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
			Print.printLine();
			return false;
		}
	}

	private void display() {

		List<Message> msgList = mailHandler.getNewMails();
		if (msgList.size() > 0) {
			MessageUtil.printMessageList(msgList);
			askMessage(msgList);
		} else {
			Print.printLine();
			Print.printString(PropertiesReader.getString("NoMsg")); //$NON-NLS-1$
			Print.printLine();
			Print.printLine();
		}
	}

	private void logout() {

		mailHandler.logout();
	}

	private void showAll() {

		List<Message> msgList = mailHandler.getAllMails();
		if (msgList.size() > 0) {
			MessageUtil.printMessageList(msgList);
			askMessage(msgList);
		} else {
			Print.printLine();
			Print.printString(PropertiesReader.getString("NoMsg")); //$NON-NLS-1$
			Print.printLine();
			Print.printLine();
		}
	}

	private void askMessage(List<Message> msgList) {

		try {

			int msgListLen = msgList.size();
			int msgId = 0;
			String input = ""; //$NON-NLS-1$
			Print.printString(PropertiesReader.getString("EnterMsgNumber")); //$NON-NLS-1$
			input = ClientUtil.getInputFromConsole();
			msgId = Integer.parseInt(input);
			while (msgId != -1) {
				if ( msgId > 0 && msgId <= msgListLen) {
					Message msgObj = msgList.get(msgId - 1);
					MessageUtil.showMessage(msgObj);
				} else {
					Print.printLine();
					Print.printString(PropertiesReader.getString("EnterValidMsgNo")); //$NON-NLS-1$
					Print.printLine();
					Print.printLine();
				}
				Print.printString(PropertiesReader.getString("EnterMsgNumber")); //$NON-NLS-1$
				input = ClientUtil.getInputFromConsole();
				msgId = Integer.parseInt(input);
			}

		} catch (IOException ioe) {
			Print.printString(PropertiesReader.getString("ConsoleReadErr")); //$NON-NLS-1$
			Print.printLine();
		}
	}

	/*
	 * Author : Harish Date : 28/12/2009 Method : compose
	 */

	private void compose() {
		Print.printLine();
		Message message = new Message();
		Print.printString(PropertiesReader.getString("To")); //$NON-NLS-1$
		message.setToUsers(MessageUtil.getToUsers());
		message.setFromUser(getEmailID());
		Print.printString(PropertiesReader.getString("Subject")); //$NON-NLS-1$
		message.setSubject(MessageUtil.getSubject());
		Print.printString(PropertiesReader.getString("Msg")); //$NON-NLS-1$
		message.setMsgBody(MessageUtil.getMessage());
		if (mailHandler.sendMail(message)) {
			Print.printString(PropertiesReader.getString("SendSuccess")); //$NON-NLS-1$
			Print.printLine();
		} else {
			Print.printString(PropertiesReader.getString("SendFail")); //$NON-NLS-1$
			Print.printLine();
		}
	}
}