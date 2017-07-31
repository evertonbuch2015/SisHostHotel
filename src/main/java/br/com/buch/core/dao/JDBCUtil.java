package br.com.buch.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCUtil {
	
	private static final String DRIVER 	= "org.firebirdsql.jdbc.FBDriver";	
	private static final String URL	 	= "jdbc:firebirdsql:192.168.1.100/3050:SISHOSTHOTEL";	
	private static final String USUARIO	= "SYSDBA";
	private static final String SENHA 	= "masterkey";	
	
		
	private static JDBCUtil instanceJDBCUtil;	
	
	private JDBCUtil() {
		
	}
	
	
	public static synchronized JDBCUtil getInstance() {
		if (instanceJDBCUtil == null) {
			instanceJDBCUtil = new JDBCUtil();
		}
		return instanceJDBCUtil;
	}

	
	public Connection getConnection() throws SQLException{
		System.setProperty("jdbc.Drivers", DRIVER); 
		return DriverManager.getConnection(URL, USUARIO, SENHA);	
	}
}
