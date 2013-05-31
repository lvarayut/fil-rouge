package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

public class BetWinnerDAO {

	public BetWinnerDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void betWinner(long numberTokens, String competition,
			PCompetitor winner, String username) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();

		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into bet_winner(pseudo_subscriber, id_competitor, name_competition, betToken)  values (?,?,?,?)");
			psSQL.setString(1, username);
			psSQL.setInt(2, winner.getId());
			psSQL.setString(3, competition);
			psSQL.setLong(4, numberTokens);
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
		System.out.println("Bet on winner is sucess!");
	}

}
