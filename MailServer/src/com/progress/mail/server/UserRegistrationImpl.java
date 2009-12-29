/**
 * @author Varun Tayal
 */
package com.progress.mail.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRegistrationImpl {
	public static void registerUsers(ArrayList<String> users) {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(PropertiesReader.getString("DBDriver")); //$NON-NLS-1$
			con = DriverManager
					.getConnection(
							PropertiesReader.getString("DBURL"), PropertiesReader.getString("Username"), //$NON-NLS-1$ //$NON-NLS-2$
							PropertiesReader.getString("Password")); //$NON-NLS-1$

			if (!con.isClosed()) {
				System.out.println(PropertiesReader.getString("ConnectionSuccessMsg")); //$NON-NLS-1$
				st = con.createStatement();
				if (users != null && users.size() != 0) {

					createUsers(users, st);
					insertUsersIntoClientsTable(users, st);
					createMailboxUserTable(users, st);
					grantAccess(users, st);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	private static void createUsers(ArrayList<String> users, Statement st)
			throws SQLException {
		StringBuilder createUserQuery = new StringBuilder("create user "); //$NON-NLS-1$
		for (String user : users) {
			if (user.trim() != "") { //$NON-NLS-1$
				createUserQuery.append("'"); //$NON-NLS-1$
				createUserQuery.append(user);
				createUserQuery.append("'"); //$NON-NLS-1$
				createUserQuery.append(" identified by "); //$NON-NLS-1$
				createUserQuery.append("'"); //$NON-NLS-1$
				createUserQuery.append(user);
				createUserQuery.append("'"); //$NON-NLS-1$
				createUserQuery.append(","); //$NON-NLS-1$
			}
		}
		if (users.size() != 1 || users.get(0).trim() != "") { //$NON-NLS-1$
			createUserQuery.deleteCharAt(createUserQuery.lastIndexOf(",")); //$NON-NLS-1$
			st.execute(createUserQuery.toString());
			System.out.println(PropertiesReader.getString("CreatedUserSuccessMsg") + users); //$NON-NLS-1$
		}
	}

	private static void insertUsersIntoClientsTable(ArrayList<String> users,
			Statement st) throws SQLException {
		StringBuilder insertUserQuery = new StringBuilder("insert into clients (email_id) values "); //$NON-NLS-1$
		for (String user : users) {
			if (user.trim() != "") { //$NON-NLS-1$
				insertUserQuery.append("('"); //$NON-NLS-1$
				insertUserQuery.append(user);
				insertUserQuery.append("')"); //$NON-NLS-1$
				insertUserQuery.append(","); //$NON-NLS-1$
			}
		}
		if (users.size() != 1 || users.get(0).trim() != "") { //$NON-NLS-1$
			insertUserQuery.deleteCharAt(insertUserQuery.lastIndexOf(",")); //$NON-NLS-1$
			st.executeUpdate(insertUserQuery.toString());
			System.out.println(PropertiesReader.getString("InsertedUserSuccessMsg") + users); //$NON-NLS-1$
		}		
	}
	
	private static void createMailboxUserTable(ArrayList<String> users,
			Statement st) throws SQLException {
		for (String user : users) {
			if (user.trim() != "") { //$NON-NLS-1$
				String createtableQuery = "create table IF NOT EXISTS mailbox_" //$NON-NLS-1$
						+ user
						+ " (msg_id int(10) unsigned not null, primary key (msg_id));"; //$NON-NLS-1$
				st.execute(createtableQuery);
				System.out.println(PropertiesReader.getString("CreatedTableSuccessMsg") + user); //$NON-NLS-1$
			}
		}
	}

	private static void grantAccess(ArrayList<String> users, Statement st)
			throws SQLException {
		StringBuilder grantExecuteAccessToSPQuery = new StringBuilder(
				"grant EXECUTE on PROCEDURE SP_UPDATE_MAILBOXES to "); //$NON-NLS-1$
		StringBuilder grantUpdateAccessToClientsTableQuery = new StringBuilder(
				"grant UPDATE on TABLE clients to "); //$NON-NLS-1$
		StringBuilder grantSelectAccessToClientsTableQuery = new StringBuilder(
				"grant SELECT on TABLE clients to "); //$NON-NLS-1$
		StringBuilder grantSelectAccessToMailboxTableQuery = new StringBuilder(
				"grant SELECT on TABLE mailbox to "); //$NON-NLS-1$

		for (String user : users) {
			if (user.trim() != "") { //$NON-NLS-1$
				StringBuilder grantSelectAccessToMailboxUserTableQuery = new StringBuilder(
						"grant SELECT on TABLE mailbox_" + user + " to '" //$NON-NLS-1$ //$NON-NLS-2$
								+ user + "' identified by '" + user + "'"); //$NON-NLS-1$ //$NON-NLS-2$
				System.out.println(grantSelectAccessToMailboxUserTableQuery
						.toString());
				st.execute(grantSelectAccessToMailboxUserTableQuery.toString());
				System.out.println("Table mailbox_" + user //$NON-NLS-1$
						+ " " +PropertiesReader.getString("SelectAccessGrantedMsg") + user); //$NON-NLS-1$
				System.out.println(grantSelectAccessToMailboxUserTableQuery
						.toString());
				grantExecuteAccessToSPQuery.append("'"); //$NON-NLS-1$
				grantExecuteAccessToSPQuery.append(user);
				grantExecuteAccessToSPQuery.append("'"); //$NON-NLS-1$
				grantExecuteAccessToSPQuery.append(" identified by "); //$NON-NLS-1$
				grantExecuteAccessToSPQuery.append("'"); //$NON-NLS-1$
				grantExecuteAccessToSPQuery.append(user);
				grantExecuteAccessToSPQuery.append("'"); //$NON-NLS-1$
				grantExecuteAccessToSPQuery.append(","); //$NON-NLS-1$

				grantUpdateAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantUpdateAccessToClientsTableQuery.append(user);
				grantUpdateAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantUpdateAccessToClientsTableQuery.append(" identified by "); //$NON-NLS-1$
				grantUpdateAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantUpdateAccessToClientsTableQuery.append(user);
				grantUpdateAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantUpdateAccessToClientsTableQuery.append(","); //$NON-NLS-1$

				grantSelectAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToClientsTableQuery.append(user);
				grantSelectAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToClientsTableQuery.append(" identified by "); //$NON-NLS-1$
				grantSelectAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToClientsTableQuery.append(user);
				grantSelectAccessToClientsTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToClientsTableQuery.append(","); //$NON-NLS-1$

				grantSelectAccessToMailboxTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToMailboxTableQuery.append(user);
				grantSelectAccessToMailboxTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToMailboxTableQuery.append(" identified by "); //$NON-NLS-1$
				grantSelectAccessToMailboxTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToMailboxTableQuery.append(user);
				grantSelectAccessToMailboxTableQuery.append("'"); //$NON-NLS-1$
				grantSelectAccessToMailboxTableQuery.append(","); //$NON-NLS-1$
			}
		}
		if (users.size() != 1 || users.get(0).trim() != "") { //$NON-NLS-1$
			grantExecuteAccessToSPQuery
					.deleteCharAt(grantExecuteAccessToSPQuery.lastIndexOf(",")); //$NON-NLS-1$
			grantUpdateAccessToClientsTableQuery
					.deleteCharAt(grantUpdateAccessToClientsTableQuery
							.lastIndexOf(",")); //$NON-NLS-1$
			grantSelectAccessToClientsTableQuery
					.deleteCharAt(grantSelectAccessToClientsTableQuery
							.lastIndexOf(",")); //$NON-NLS-1$
			grantSelectAccessToMailboxTableQuery
					.deleteCharAt(grantSelectAccessToMailboxTableQuery
							.lastIndexOf(",")); //$NON-NLS-1$

			System.out.println(grantExecuteAccessToSPQuery.toString());
			System.out.println(grantUpdateAccessToClientsTableQuery.toString());
			System.out.println(grantSelectAccessToClientsTableQuery.toString());
			System.out.println(grantSelectAccessToMailboxTableQuery.toString());

			st.execute(grantExecuteAccessToSPQuery.toString());
			st.execute(grantUpdateAccessToClientsTableQuery.toString());
			st.execute(grantSelectAccessToClientsTableQuery.toString());
			st.execute(grantSelectAccessToMailboxTableQuery.toString());
			System.out.println(PropertiesReader.getString("ExecuteAccessGrantedMsg") //$NON-NLS-1$
					+ users);
			System.out
					.println("Table clients "+PropertiesReader.getString("UpdateAccessGrantedMsg") //$NON-NLS-1$
							+ users);
			System.out
					.println("Table clients "+PropertiesReader.getString("SelectAccessGrantedMsg") //$NON-NLS-1$
							+ users);
			System.out
					.println("Table mailbox "+PropertiesReader.getString("SelectAccessGrantedMsg") //$NON-NLS-1$
							+ users);
		}
	}

}