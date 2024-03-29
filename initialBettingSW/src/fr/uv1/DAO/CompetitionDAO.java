
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
import fr.uv1.bettingServices.ExistingCompetitionException;
import fr.uv1.bettingServices.ExistingCompetitorException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.database.DBConnection;

/**
 * This class is used to create t
 * @author Rokhaya and Varayut
 * @version 2.0
 * @since 24/05/2013
 *
 */
public class CompetitionDAO {

	public CompetitionDAO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add a new competition
	 * @param com Competition's name
	 * @throws SQLException SQL problem
	 * @throws ExistingCompetitionException The competition is already existed
	 */

	public void addCompetition(Competition com) throws SQLException, ExistingCompetitionException {
		// Verify duplicate competition
		if(isExistCompetition(com.getName()))
			throw new ExistingCompetitionException("Duplicate competition");
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
	/**
	 * Add a new Competitor
	 * @param a_competition Competition's name
	 * @param competitors Collection of competitors
	 * @throws SQLException SQL problem
	 */
	public void addCompetitor(Competition a_competition,
			Collection competitors) throws SQLException {
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		CompetitorDAO cd = new CompetitorDAO();
		try {
			Iterator iterator = competitors.iterator();
			while (iterator.hasNext()) {
				PCompetitor tempC = (PCompetitor) iterator.next();
				// Verify whether this competitor is already created or not
				if(!cd.isExistCompetitor(tempC.getId())){
					tempC = cd.createCompetitor(tempC);
				}
				c.setAutoCommit(false);
				PreparedStatement psSQL = c
						.prepareStatement("insert into participate(id_competitor,name_competition)  values (?,?)");
				psSQL.setInt(1, tempC.getId());
				psSQL.setString(2, a_competition.getName());
				psSQL.executeUpdate();
				psSQL.close();
				System.out.println("Sucess!, the competitor id = "+tempC.getId()+" participated in the competition");
			}

		} catch (SQLException | ExistingCompetitorException e) {
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
	
	
	
	/**
	 * List all competitions in the database
	 * @return Collection of competitions
	 * @throws SQLException SQL problem
	 */
	public Collection<Competition> listAllCompetition() throws SQLException{
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		Collection<Competition> allCTion = new ArrayList<Competition>();
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
	
	/**
	 * Verify the existence of competition
	 * @param competitionName Competition's name
	 * @return Whether the competition is existed or not
	 * @throws SQLException SQL problem
	 */
	public boolean isExistCompetition(String competitionName) throws SQLException{
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		PreparedStatement psSQL = c
				.prepareStatement("select * from competition");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			if(competitionName.equals(resultSet.getString("name")))
				return true;
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
		return false;
	}
}
