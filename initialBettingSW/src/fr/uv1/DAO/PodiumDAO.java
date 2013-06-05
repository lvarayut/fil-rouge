package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

/**
 * This class is used to create t
 * 
 * @author Rokhaya and Varayut
 * @version 2.0
 * @since 24/05/2013
 * 
 */
public class PodiumDAO {

	public PodiumDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Distribute tokens for subscribers who win this competition
	 * 
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @param second
	 *            First runner-up
	 * @param third
	 *            Second runner-up
	 * @throws SQLException
	 *             SQL Problem
	 */
	public void settlePodiumToSubscriber(String competition,
			PCompetitor winner, PCompetitor second, PCompetitor third)
			throws SQLException {
		int totalTokensInCompetition = 0;
		int totalTokensInPodium = 0;
		int numberOfRightSub = 0;
		ResultSet resultSet = null;
		PreparedStatement psSQL = null;
		SubscriberDAO sd = new SubscriberDAO();
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		// Calculate the total number of tokens are bet in podium
		psSQL = c
				.prepareStatement("Select sum(betToken) Total from bet_podium where name_competition = ?");
		psSQL.setString(1, competition);
		resultSet = psSQL.executeQuery();
		resultSet.next();
		totalTokensInPodium = resultSet.getInt("Total");

		// Calculate the total number of tokens are bet in this competition
		psSQL = c
				.prepareStatement("Select sum(betToken) from bet_winner where name_competition = ?");
		psSQL.setString(1, competition);
		resultSet = psSQL.executeQuery();
		resultSet.next();
		totalTokensInCompetition = resultSet.getInt(1) + totalTokensInPodium;
		// Distribute tokens
		psSQL = c
				.prepareStatement("Select * from bet_podium where name_competition = ? and id_competitor1 = ? and  id_competitor2 = ? and id_competitor3 = ?");
		psSQL.setString(1, competition);
		psSQL.setInt(2, winner.getId());
		psSQL.setInt(3, second.getId());
		psSQL.setInt(4, third.getId());
		resultSet = psSQL.executeQuery();
		// Move a index cursor to the last position
		resultSet.last();
		// Get the last position
		numberOfRightSub = resultSet.getRow();
		resultSet.first();
		// If no subscriber bets on the right podium, the tokens bet are
		// credited to subscribers betting on the competition
		if (numberOfRightSub == 0) {
			psSQL = c
					.prepareStatement("Select * from bet_podium where name_competition = ?");
			psSQL.setString(1, competition);
			resultSet = psSQL.executeQuery();
			while (resultSet.next()) {
				sd.addTokens(resultSet.getString("pseudo_subscriber"),
						resultSet.getInt("betToken"));
			}
		}
		// number of tokens betted * total tokens betted for the competition) /
		// total number of tokens betted for the podium
		else {
			do {
				int tokens = resultSet.getInt("betToken");
				String subscriber = resultSet.getString("pseudo_subscriber");
				sd.addTokens(subscriber, tokens * totalTokensInCompetition
						/ totalTokensInPodium);
			} while (resultSet.next());
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
	}

	/**
	 * Add the competitors who are first, second, and third
	 * 
	 * @param competition Competition's name
	 * @param winner
	 *            Winner
	 * @param second
	 *            First runner-up
	 * @param third
	 *            Second runner-up
	 * @throws SQLException SQL problem
	 */
	public void addPodiumWinner(String competition, PCompetitor winner,
			PCompetitor second, PCompetitor third) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();

		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into podium(name_competition, id_competitor1, id_competitor2,id_competitor3)  values (?, ?,?,?)");
			psSQL.setString(1, competition);
			psSQL.setInt(2, 464);
			psSQL.setInt(3, 465);
			psSQL.setInt(4, 466);
			psSQL.executeUpdate();
			psSQL.close();
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
		System.out.println("Podium is calculated!");
	}

}
