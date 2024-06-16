package com.crud.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Connection conn=null;
	
	public static Connection getConnection() throws SQLException {
		if(conn!=null) {
			return conn;
		}else {
			try {
				Class.forName(DBinfo.DRIVER_NAME);
				conn = DriverManager.getConnection(DBinfo.URL, DBinfo.USER_NAME, DBinfo.PASSWORD);
				return conn;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return conn;
		}
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(getConnection());
	}
	
}
