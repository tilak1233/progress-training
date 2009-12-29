package com.progress.mail.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.progress.mail.client.msg.Message;
import com.progress.mail.impl.PropertiesReader;

public class DBUtil {

	public static Connection createConnection(String username, String password) throws SQLException {
        String url = PropertiesReader.getString("dbURL"); //$NON-NLS-1$
        Connection conn = null;
        try {
			Class.forName (PropertiesReader.getString("dbDriver")).newInstance (); //$NON-NLS-1$
			conn = DriverManager.getConnection (url, username, password);		
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static List<Message> getMessagesFromDB(String query, Connection conn) throws SQLException {
		List<Message> msgs = new ArrayList<Message>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		populateMessagesList(msgs, rs);
		return msgs;
	}
	
	public static void populateMessagesList(List<Message> msgs, ResultSet rs) throws SQLException {
		while(rs.next()){
			Message msg = new Message();
			msg.setMsgId(rs.getInt(PropertiesReader.getString("msgId"))); //$NON-NLS-1$
			msg.setFromUser(rs.getString(PropertiesReader.getString("msg_from"))); //$NON-NLS-1$
			msg.setToUsers(getToUsersList(rs.getString(PropertiesReader.getString("msg_to")))); //$NON-NLS-1$
			msg.setSubject(rs.getString(PropertiesReader.getString("msg_subj"))); //$NON-NLS-1$
			msg.setMsgBody(rs.getString(PropertiesReader.getString("msg_body"))); //$NON-NLS-1$
			msg.setTimestamp(rs.getTimestamp(PropertiesReader.getString("msg_time"))); //$NON-NLS-1$
			msgs.add(msg);
		}
	}

	public static List<String> getToUsersList(String toUsersList) {
		String[] users = toUsersList.split(","); //$NON-NLS-1$
		List<String> usersList = new ArrayList<String>();
		for (int i = 0; i < users.length; i++) {
			String user = users[i].trim();
			if( user != null && user.length() != 0 )
				usersList.add(user);
		}
		return usersList;
	}

	public static void executeUpdateQuery(String updateQuery, Connection conn) throws SQLException {
		conn.createStatement().executeUpdate(updateQuery);
		
	}
	
}
