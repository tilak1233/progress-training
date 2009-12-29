package com.progress.mail.impl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesReader {
	private static final String BUNDLE_NAME = "com.progress.mail.impl.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private PropertiesReader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
