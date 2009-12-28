package com.progress.mail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.progress.mail.client.msg.Message;

public class MailHandler {
	
	public List<Message> getAllMails() {
		return null;
	}
	
	public boolean isValidUuser(String username, String password) {
		return false;
	}
	
	public List<Message> getNewMails(String clientId) {
		return null;
	}

	public void sendMail(Message msg) {
		
	}
	
	public static void main(String[] args) {
		Connection conn = null;

        try
        {
            String userName = "root";
            String password = "root";
            String url = "jdbc:mysql://nbhydvtayal:3306/emailsys_schema";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from clients");
            while (rs.next()){
            	System.out.println(rs.getString(1));
            }
        }	
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close ();
                    System.out.println ("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */ }
            }
        }
    }

}
