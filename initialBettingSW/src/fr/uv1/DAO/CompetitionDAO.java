package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

public class CompetitionDAO {

	public CompetitionDAO() {
		// TODO Auto-generated constructor stub
	}
	public void addCompetition(Competition com) throws SQLException {
		DBConnection db = new DBConnection();
		java.sql.Date startDay = new java.sql.Date(com.getStartDate().getTime()
				.getTime());
		java.sql.Date endDay = new java.sql.Date(com.getEndDate().getTime()
				.getTime());
		Connection c = db.connect();
		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into competition(name,startDate,endDate)  values (?,?,?)");

			psSQL.setString(1, com.getName());
			psSQL.setDate(2, startDay);
			psSQL.setDate(3, endDay);
			psSQL.executeUpdate();
			psSQL.close();
			System.out.println("Sucess!, the competition is added");

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
	public void addCompetitor(Competition a_competition,
			Collection competitors) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		try {
			Iterator iterator = competitors.iterator();
			while (iterator.hasNext()) {
				PCompetitor tempC = (PCompetitor) iterator.next();
				c.setAutoCommit(false);
				PreparedStatement psSQL = c
						.prepareStatement("insert into participate(id_competitor,name_competition)  values (?,?)");
				
				psSQL.setString(1, a_competition.getName());
				psSQL.executeUpdate();
				psSQL.close();
				System.out.println("Sucess!, the competitors participated in the competition");
			}

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
	
	public Collection listAllCompetition() throws SQLException{
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		Collection allCTion = new ArrayList<PCompetitor>();
		PreparedStatement psSQL = c
				.prepareStatement("select * from competition");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.setTime(resultSet.getDate("startDate"));
			endDate.setTime(resultSet.getDate("endDate"));
			allCTion.add(new Competition(resultSet.getString("name"),startDate,endDate));
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
		return allCTion;
	}
}
