package com.progress.mail.server;

import java.util.ArrayList;

/***
 * This class is to run UserRegistrationImpl Class's registerUsers() method
 * which takes Arraylist of names of users to be registered.
 *  
 * @author Varun Tayal
 *
 */

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
