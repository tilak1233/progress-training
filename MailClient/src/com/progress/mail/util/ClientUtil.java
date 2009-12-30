package com.progress.mail.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.progress.mail.impl.PropertiesReader;

/**
 * This class has the utility methods of client , to get input , print etc...
 * @author pchaitan
 * @since 29/12/2009
 */

public class ClientUtil {

	public static String getInputFromConsole() throws IOException {

		InputStreamReader inReader = new InputStreamReader(System.in);
		BufferedReader buffReader = new BufferedReader(inReader);
		String input = buffReader.readLine();
		while(input.equalsIgnoreCase("")) { //$NON-NLS-1$
			Print.printLine();
			Print.printString(PropertiesReader.getString("EnterNumber")); //$NON-NLS-1$
			input = buffReader.readLine();
		}
		return input;
	}
	
	public static void printMailMenu() {

		Print.printLine();
		Print.printString(PropertiesReader.getString("Menu")); //$NON-NLS-1$
		Print.printLine();

		Print.printInteger(1);
		Print.printString(PropertiesReader.getString("ShowAll")); //$NON-NLS-1$
		Print.printLine();

		Print.printInteger(2);
		Print.printString(PropertiesReader.getString("Display")); //$NON-NLS-1$
		Print.printLine();

		Print.printInteger(3);
		Print.printString(PropertiesReader.getString("Compose")); //$NON-NLS-1$
		Print.printLine();

		Print.printInteger(4);
		Print.printString(PropertiesReader.getString("Logout")); //$NON-NLS-1$
		Print.printLine();
		Print.printLine();

		Print.printString(PropertiesReader.getString("EnterCommandNumber")); //$NON-NLS-1$
	}
}
