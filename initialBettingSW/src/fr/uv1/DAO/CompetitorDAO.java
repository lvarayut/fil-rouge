package fr.uv1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

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
public class CompetitorDAO {

	public CompetitorDAO() {
		// TODO Auto-generated constructor stub
	}
	/** Create a new competitor
	 * @param com Competitor's name
	 * @return Competitor object
	 * @throws SQLException SQL problem
	 * @throws ExistingCompetitorException The competitor is already existed
	 */
	public PCompetitor createCompetitor(PCompetitor com) throws SQLException, ExistingCompetitorException {
		// Verify duplicate competitor
		if(isExistCompetitor(com.getId()))
				throw new ExistingCompetitorException("Duplicate competitor");
		int id = 0;
		DBConnection db = new DBConnection();
		java.sql.Date birthDay = new java.sql.Date(com.getBirthdate().getTime()
				.getTime());
		Connection c = db.connect();
		try {
			c.setAutoCommit(false);
			PreparedStatement psSQL = c
					.prepareStatement("insert into competitor(firstname, lastname,birthdate)  values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			//id = 
			//psSQL.setInt(1,Statement.RETURN_GENERATED_KEYS);
			psSQL.setString(1, com.getFirstname());
			psSQL.setString(2, com.getLastname());
			psSQL.setDate(3, birthDay);
			psSQL.executeUpdate();
			
			// Retrieve last index
			ResultSet keys = psSQL.getGeneratedKeys();
            while (keys.next()) {
                id = keys.getInt(1);
            }
            com.setId(id);
            // Release object
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
		return com;

	}
	
	/**
	 * Verify the existence of competitor
	 * @param id Competitor's id
	 * @return Whether the competitor is existed or not
	 * @throws SQLException SQL problem
	 */
	public boolean isExistCompetitor(int id) throws SQLException{
		DBConnection db = new DBConnection();
		Connection c = db.connect();
		PreparedStatement psSQL = c
				.prepareStatement("select * from competitor");
		ResultSet resultSet = psSQL.executeQuery();
		while (resultSet.next()) {
			if(id == resultSet.getInt("id"))
				return true;
		}
		resultSet.close();
		c.setAutoCommit(true);
		db.disconnect();
		return false;
	}


}
