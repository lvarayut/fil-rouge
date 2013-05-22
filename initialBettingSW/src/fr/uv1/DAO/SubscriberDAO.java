package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.uv1.bettingServices.ExistingCompetitorException;
import fr.uv1.bettingServices.ExistingSubscriberException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.database.DBConnection;

public class SubscriberDAO {

	public SubscriberDAO() {
		// TODO Auto-generated constructor stub
	}

	public void addSubscriber(Subscriber s) throws SQLException, ExistingSubscriberException {
		// Verify duplicate subscriber
		if(isDuplicateSubscriber(s))
			throw new ExistingSubscriberException("Duplicate subscriber");
		DBConnection db = new DBConnection();
		java.sql.Date birthDay = new java.sql.Date(s.getBirthdate().getTime()
				.getTime());
		Connection c = db.connect();
		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into subscriber(pseudo, lastname, firstname,password,birthdate,number_token)  values (?, ?,?,?,?,?)");

			psSQL.setString(1, s.getUsername());
			psSQL.setString(2, s.getLastName());
			psSQL.setString(3, s.getFirstName());
			psSQL.setString(4, s.getPassword());
			psSQL.setDate(5, birthDay);
			psSQL.setInt(6, s.getToken());
			psSQL.executeUpdate();
			psSQL.close();
			System.out.println("Sucess!, the subscriber is added");

		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			c.setAutoCommit(true);
			System.err.println("There is an error, please see following :");
			System.err.println(e.getMessage());
		}
		c.setAutoCommit(true);
		db.disconnect();
	}
	public boolean isDuplicateSubscriber(Subscriber s) throws SQLException{
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		PreparedStatement psSQL = c
				.prepareStatement("select * from subscriber");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			if(s.getUsername().equals(resultSet.getString("pseudo")))
				return true;
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
		return false;
	}
	public void addTokens(String username, long numberTokens)
			throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		long tokens = 0;
		// Retrieve current user's tokens
		PreparedStatement psSQL = c
				.prepareStatement("select number_token from subscriber where pseudo = ?");
		psSQL.setString(1, username);
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			tokens = resultSet.getLong("number_token");
		}
		tokens += numberTokens;
		// Calculate and add new tokens
		psSQL = c
				.prepareStatement("update subscriber set number_token = ? where pseudo = ?");
		psSQL.setLong(1, tokens);
		psSQL.setString(2, username);
		psSQL.executeUpdate();
		psSQL.close();
		db.disconnect();
		System.out.println("The username : "+username+" has  "+tokens+" tokens");
	}

	public void removeTokens(String username, long numberTokens) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		long tokens = 0;
		// Retrieve current user's tokens
		PreparedStatement psSQL = c
				.prepareStatement("select number_token from subscriber where pseudo = ?");
		psSQL.setString(1, username);
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			tokens = resultSet.getLong("number_token");
		}
		tokens += numberTokens;
		// Calculate and add new tokens
		psSQL = c
				.prepareStatement("update subscriber set number_token = ? where pseudo = ?");
		psSQL.setLong(1, tokens);
		psSQL.setString(2, username);
		psSQL.executeUpdate();
		psSQL.close();
		db.disconnect();
		System.out.println("The username : "+username+" has  "+tokens+" tokens");
	}

}
