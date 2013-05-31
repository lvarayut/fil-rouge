package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.database.DBConnection;

public class BetPodiumDAO {

	public BetPodiumDAO() {
		// TODO Auto-generated constructor stub
	}

	public void betPodium(long numberTokens, String competition,
			PCompetitor winner, PCompetitor second, PCompetitor third,
			String username) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();

		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into bet_podium(pseudo_subscriber, id_competitor1, id_competitor2, id_competitor3, name_competition, betToken)  values (?,?,?,?,?,?)");
			psSQL.setString(1, username);
			psSQL.setInt(2, winner.getId());
			psSQL.setInt(3, second.getId());
			psSQL.setInt(4, third.getId());
			psSQL.setString(5, competition);
			psSQL.setLong(6, numberTokens);
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
		System.out.println("Bet on podium is sucess!");
	}

	
}
