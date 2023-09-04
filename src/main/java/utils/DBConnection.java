package utils;

import config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DRIVER = "org.sqlite.JDBC";
	private static Connection connection;
	public static Connection getConnection(){
		if (connection == null) {
			// 数据库驱动加载
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(Config.URL);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
        }
		return connection;
	}
}
