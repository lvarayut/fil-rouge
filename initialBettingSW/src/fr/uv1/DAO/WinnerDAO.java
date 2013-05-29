package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

public class WinnerDAO {

	public WinnerDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Distribute tokens for subscribers who win this competition
	 * 
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @throws SQLException
	 *             SQL Problem
	 */
	public void settleWinnerToSubscriber(String competition, PCompetitor winner)
			throws SQLException {
		int totalTokensInCompetition = 0;
		int totalTokensInWinner = 0;
		int numberOfRightSub = 0;
		ResultSet resultSet = null;
		PreparedStatement psSQL = null;
		SubscriberDAO sd = new SubscriberDAO();
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		// Calculate the total number of tokens are bet in winner
		psSQL = c
				.prepareStatement("Select sum(betToken) Total from bet_winner where name_competition = ?");
		psSQL.setString(1, competition);
		resultSet = psSQL.executeQuery();
		resultSet.next();
		totalTokensInWinner = resultSet.getInt("Total");

		// Calculate the total number of tokens are bet in this competition
		psSQL = c
				.prepareStatement("Select sum(betToken) from bet_podium where name_competition = ?");
		psSQL.setString(1, competition);
		resultSet = psSQL.executeQuery();
		resultSet.next();
		totalTokensInCompetition = resultSet.getInt(1) + totalTokensInWinner;
		// Distribute tokens
		psSQL = c
				.prepareStatement("Select * from bet_winner where name_competition = ? and id_competitor = ?");
		psSQL.setString(1, competition);
		psSQL.setInt(2, winner.getId());
		resultSet = psSQL.executeQuery();
		// Move a index cursor to the last position
		resultSet.last();
		// Get the last position
		numberOfRightSub = resultSet.getRow();
		resultSet.first();
		// If no subscriber bets on the right winner, the tokens bet are
		// credited to subscribers betting on the competition
		if (numberOfRightSub == 0) {
			psSQL = c
					.prepareStatement("Select * from bet_winner where name_competition = ?");
			psSQL.setString(1, competition);
			resultSet = psSQL.executeQuery();
			while (resultSet.next()) {
				sd.addTokens(resultSet.getString("pseudo_subscriber"),
						resultSet.getInt("betToken"));
			}
		}
		// number of tokens betted * total tokens betted for the competition) /
		// total number of tokens betted for the winner
		else {
			do {
				int tokens = resultSet.getInt("betToken");
				String subscriber = resultSet.getString("pseudo_subscriber");
				sd.addTokens(subscriber, tokens * totalTokensInCompetition
						/ totalTokensInWinner);
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
	 * @throws SQLException SQL problem
	 */
	public void addWinner(String competition, PCompetitor winner) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();

		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into winner(name_competition, id_competitor)  values (?, ?)");
			psSQL.setString(1, competition);
			psSQL.setInt(2, winner.getId());
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
		System.out.println("Winner is calculated!");
	}
}
