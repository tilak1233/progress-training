package com.progress.mail.server;

import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {
		ArrayList<String> users = new ArrayList<String>();
		for(int i =0; i<10; i++)
			users.add("client"+i);
		users.add("vtayal");
		users.add("stilak");
		users.add("desingh");
		users.add("cmittapa");
		users.add("hgarapat");
		users.add("pchaitan");
		UserRegistrationImpl.registerUsers(users);
	}

}
