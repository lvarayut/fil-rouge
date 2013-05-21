package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

public class CompetitorDAO {

	public CompetitorDAO() {
		// TODO Auto-generated constructor stub
	}
	public void createCompetitor(PCompetitor com) throws SQLException {
		DBConnection db = new DBConnection();
		java.sql.Date birthDay = new java.sql.Date(com.getBirthdate().getTime()
				.getTime());
		Connection c = db.connect();
		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into competitor(id,firstname, lastname,birthdate)  values (?,?,?,?)");

			psSQL.setInt(1,Statement.RETURN_GENERATED_KEYS);
			psSQL.setString(2, com.getFirstname());
			psSQL.setString(3, com.getLastname());
			psSQL.setDate(4, birthDay);
			psSQL.executeUpdate();

			psSQL.close();
			System.out.println("Sucess!, the competitor is added");

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


}
