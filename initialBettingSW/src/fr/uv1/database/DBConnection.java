package fr.uv1.database;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.*;

public class DBConnection {
	private Connection connect;
	private Statement statement;
	private String dbUrl;
	private String username;
	private String password;
	private String dbClass;

	public DBConnection() {
		this.dbUrl = "jdbc:mysql://localhost:8889/betting";
		this.dbClass = "com.mysql.jdbc.Driver";
		this.username = "root";
		this.password = "root";

	}

	public Connection connect() {
		try {
			Class.forName(dbClass);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(dbUrl, username, password);
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;

	}

	public void disconnect(){
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Select data from the database
	 * @param query
	 * @return ResultSet object which contains data
	 */
	public ResultSet getData(String query){
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Insert/Update/Delete data in the database
	 * @param query
	 * @return 0/ positive number for number of execution/ -1 for an error
	 */
	public int updateData(String query){
		int status = -1;
		try {
			status = statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		return status;
	}
	

/*	public ResultSet view(String tableName){
		String query = "Select * from "+tableName;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(query);
		} catch (Exception e) {
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		return rs;
	}


	public int insert(String tableName, ArrayList<Object> value) {
		int status = -1;
		String query = "Insert into " + tableName + " values (";
		for (int i = 0; i < value.size(); i++) {
			Object obj = value.get(i);
			if (obj.getClass().equals(String.class)) {
				query += "'" + value.get(i) + "'";
			} else if (obj.getClass().equals(Integer.class)
					|| obj.getClass().equals(Double.class)
					|| obj.getClass().equals(Float.class)) {
				query += value.get(i);
			}
			query += ", ";
		}
		query += ")";
		try {
			status = statement.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		// The status can be either 0 or number of row manipulated
		return status;
	}

	public int update(String tableName, String colNameSet,
			String colNameCondition, String valueSet, String valueCondition) {
		int status = -1;
		String query = "Update " + tableName + " set " + colNameSet + "="
				+ valueSet + " where " + colNameCondition + " = "
				+ valueCondition;
		try {
			status = statement.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		// The status can be either 0 or number of row manipulated
		return status;
	}

	public int delete(String tableName, String colName, String value) {
		int status = -1;
		String query = "Delete from " + tableName + " where " + colName + " = "
				+ value;
		try {
			status = statement.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		// The status can be either 0 or number of row manipulated
		return status;
	}*/
	

}
