package com.progress.mail.test;

import java.util.ArrayList;
import java.util.List;

import com.progress.mail.client.msg.Message;
import com.progress.mail.impl.MailHandlerImpl;
import com.progress.mail.util.Print;

import junit.framework.TestCase;

public class ClientTest extends TestCase {

	private MailHandlerImpl mailHandler;
	Message expectedmessage;

	@Override
	protected void setUp() throws Exception {
		mailHandler = new MailHandlerImpl();
		Print.printString("Set up");
		Print.printLine();
	}
	
	public void testInvalidLogin() throws Exception {
		Print.printString("testInvalidLogin");
		Print.printLine();
		boolean isValidUser = mailHandler.isValidUser("invalid", "invalid");
		assertFalse(isValidUser);
	}
	
	public void testIsValidUser() {
		assertTrue(mailHandler.isValidUser("hgarapat", "hgarapat"));
		assertTrue(mailHandler.isValidUser("stilak", "stilak"));
		assertFalse(mailHandler.isValidUser("stilak", "hgarapat"));
		assertFalse(mailHandler.isValidUser("cmittapa", "hgarapat"));
		assertFalse(mailHandler.isValidUser("vtayal", "hgarapat"));
	}

	public void testSendMail() {
		// Test Case - 1
		assertTrue(mailHandler.isValidUser("hgarapat", "hgarapat"));
		expectedmessage = new Message();
		expectedmessage.setFromUser("hgarapat");
		List<String> list = new ArrayList<String>();
		list.add("hgarapat");
		expectedmessage.setToUsers(list);
		expectedmessage.setMsgBody("hi harish");
		expectedmessage.setSubject("hi");
		assertTrue(mailHandler.sendMail(expectedmessage));
		List<Message> checkList = new ArrayList<Message>();
		checkList = mailHandler.getNewMails();
		Message checkMessage = checkList.get(0);
		assertEquals("hgarapat", checkMessage.getFromUser());
		assertEquals("hgarapat", checkMessage.getToUsers().get(0));
		assertEquals("hi", checkMessage.getSubject());
		assertEquals("hi harish", checkMessage.getMsgBody());
		mailHandler.logout();

		// Test Case - 2
		assertTrue(mailHandler.isValidUser("hgarapat", "hgarapat"));
		expectedmessage = new Message();
		expectedmessage.setFromUser("hgarapat");
		list = new ArrayList<String>();
		list.add("stilak");
		expectedmessage.setToUsers(list);
		expectedmessage.setMsgBody("hi tilak");
		expectedmessage.setSubject("hi");
		assertTrue(mailHandler.sendMail(expectedmessage));
		mailHandler.logout();

		assertTrue(mailHandler.isValidUser("stilak", "stilak"));
		List<Message> checkLst = new ArrayList<Message>();
		checkLst = mailHandler.getNewMails();
		Message checkMessg = checkLst.get(0);
		assertEquals("hgarapat", checkMessg.getFromUser());
		assertEquals("stilak", checkMessg.getToUsers().get(0));
		assertEquals("hi", checkMessg.getSubject());
		assertEquals("hi tilak", checkMessg.getMsgBody());

		// Test Case - 3
		expectedmessage = new Message();
		expectedmessage.setFromUser("stilak");
		list = new ArrayList<String>();
		list.add(" ");
		expectedmessage.setToUsers(list);
		expectedmessage.setMsgBody("hi harish");
		expectedmessage.setSubject("hi");
		assertFalse(mailHandler.sendMail(expectedmessage));
		mailHandler.logout();
	}
	
	public void testSendMailToMultipleUsers() throws Exception {
		
		Print.printString("testSendMailToMultipleUsers");
		Print.printLine();
		assertTrue(mailHandler.isValidUser("pchaitan", "pchaitan"));
		
		Message msgObj = new Message();
		msgObj.setFromUser("pchaitan");
		msgObj.setSubject("hi");
		msgObj.setMsgBody("msg body");
//		msgObj.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		List<String> toUsers = new ArrayList<String>();
		toUsers.add("vtayal");
		toUsers.add("stilak");
		toUsers.add("cmittapa");
		msgObj.setToUsers(toUsers);
		
		assertTrue(mailHandler.sendMail(msgObj));
	}
	
	public void testConnection() throws Exception {
		
		Print.printString("testConnection");
		assertNull(mailHandler.getConnection());
	}
	
	@Override
	protected void tearDown() throws Exception {
		mailHandler = null;
		Print.printString("teardown");
		Print.printLine();
	}

}
