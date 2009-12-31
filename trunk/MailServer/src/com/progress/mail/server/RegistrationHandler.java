package com.progress.mail.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * This class is to handle registration of users through UserRegistrationImpl
 * Class's registerUsers() method which takes Arraylist of names of users to be
 * registered.
 * 
 * @author Varun Tayal
 * 
 */

public class RegistrationHandler {

	public static void main(String[] args) {
		if (args !=null && args.length != 0){
			UserRegistrationImpl.registerUsers((Arrays.asList(args)));
		}
		else {
			System.out.println("No users provided.");
		}
		/*List<String> users = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
			users.add("client" + i);
		users.add("vtayal");
		users.add("stilak");
		users.add("desingh");
		users.add("cmittapa");
		users.add("hgarapati");
		users.add("pchaitanya");
		UserRegistrationImpl.registerUsers(users);*/
	}

}
