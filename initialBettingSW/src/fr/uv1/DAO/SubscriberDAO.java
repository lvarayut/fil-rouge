package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.ExistingCompetitorException;
import fr.uv1.bettingServices.ExistingSubscriberException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.database.DBConnection;
/**
 * This class is used to create t
 * @author Rokhaya and Varayut
 * @version 2.0
 * @since 24/05/2013
 *
 */
public class SubscriberDAO {

	public SubscriberDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Add a new subscriber to the database
	 * @param s Subscriber object
	 * @throws SQLException SQL problem
	 * @throws ExistingSubscriberException  The subscriber is already existed
	 */
	public void addSubscriber(Subscriber s) throws SQLException,
			ExistingSubscriberException {
		// Verify duplicate subscriber
		if (isExistSubscriber(s.getUsername()))
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
			psSQL.setString(2, s.getLastname());
			psSQL.setString(3, s.getFirstname());
			psSQL.setString(4, s.getPassword());
			psSQL.setDate(5, birthDay);
			psSQL.setInt(6, s.getToken());
			psSQL.executeUpdate();
			psSQL.close();
			System.out.println("Sucess!, the subscriber is added");
			c.commit();

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
		c.close();
		db.disconnect();
	}

	/**
	 * Verify the existence of subscriber
	 * @param username Subscriber's username
	 * @return Whether the subscriber is existed or not
	 * @throws SQLException SQL problem
	 */
	public boolean isExistSubscriber(String username) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		PreparedStatement psSQL = c
				.prepareStatement("select * from subscriber");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			if (username.equals(resultSet.getString("pseudo")))
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
		System.out.println("The username : " + username + " has  " + tokens
				+ " tokens");
	}

	public void removeTokens(String username, long numberTokens)
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
		System.out.println("The username : " + username + " has  " + tokens
				+ " tokens");
	}

	/**
	 * List all subscribers in the database
	 * 
	 * @return Collection of competitions
	 * @throws SQLException
	 *             SQL problem
	 * @throws BadParametersException 
	 */
	public Collection<Subscriber> listAllSubscriber() throws SQLException, BadParametersException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		Collection<Subscriber> allSub = new ArrayList<Subscriber>();
		PreparedStatement psSQL = c
				.prepareStatement("select * from subscriber");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			Calendar birthDate = Calendar.getInstance();
			birthDate.setTime(resultSet.getDate("birthdate"));
			Subscriber tempS = new Subscriber(resultSet.getString("lastname"),
					resultSet.getString("firstname"),
					resultSet.getString("pseudo"), birthDate);
			tempS.setPassword(resultSet.getString("password"));
			tempS.setToken(resultSet.getInt("number_token"));

			allSub.add(tempS);
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
		return allSub;
	}

}
