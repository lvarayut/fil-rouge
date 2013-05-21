package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.uv1.bettingServices.Subscriber;
import fr.uv1.database.DBConnection;

public class SubscriberDAO {

	public SubscriberDAO() {
		// TODO Auto-generated constructor stub
	}
	public void addSubscriber(Subscriber s) throws SQLException {
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

}
