package com.progress.mail.util;

import java.sql.Timestamp;

public class Print {

	public static void printString(String str) {
		System.out.print(str);
	}
	
	public static void printInteger(Integer i) {
		System.out.print(i);
	}
	
	public static void printLine() {
		System.out.println();
	}
	
	public static void printTab() {
		System.out.print("\t");
	}
	
	public static void printTime(Timestamp timeStamp) {
		System.out.print(timeStamp);
	}
}
