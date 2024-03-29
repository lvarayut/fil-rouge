package fr.uv1.bettingServices;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.CompetitorDAO;


public class PCompetitor extends Person  implements Competitor{
	private int id;
	public PCompetitor(String firstname,String lastname,Calendar birthDay) {
		// TODO Auto-generated constructor stub
		super(firstname, lastname,birthDay);
		id = -1;
	}
	
	public static boolean existCompetitor(int id) throws SQLException{
		CompetitorDAO cd = new CompetitorDAO();
		return cd.isExistCompetitor(id);
	}

	/**
	 * uml.property  name="betPodium"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.BetPodium"
	 */
	private Collection<BetPodium> betPodium;

	/**
	 * Getter of the property <tt>betPodium</tt>
	 * @return  Returns the betPodium.
	 * uml.property  name="betPodium"
	 */
	public Collection<BetPodium> getBetPodium() {
		return betPodium;
	}

	/**
	 * Setter of the property <tt>betPodium</tt>
	 * @param betPodium  The betPodium to set.
	 * uml.property  name="betPodium"
	 */
	public void setBetPodium(Collection betPodium) {
		this.betPodium = betPodium;
	}

	/**
	 * uml.property  name="betWinner"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.BetWinner"
	 */
	private Collection betWinner;

	/**
	 * Getter of the property <tt>betWinner</tt>
	 * @return  Returns the betWinner.
	 * uml.property  name="betWinner"
	 */
	public Collection<BetWinner> getBetWinner() {
		return betWinner;
	}

	/**
	 * Setter of the property <tt>betWinner</tt>
	 * @param betWinner  The betWinner to set.
	 * uml.property  name="betWinner"
	 */
	public void setBetWinner(Collection<BetWinner> betWinner) {
		this.betWinner = betWinner;
	}

	/**
	 * uml.property  name="competitionPodium"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.CompetitionPodium"
	 */
	private Collection<CompetitionPodium> competitionPodium;

	/**
	 * Getter of the property <tt>competitionPodium</tt>
	 * @return  Returns the competitionPodium.
	 * uml.property  name="competitionPodium"
	 */
	public Collection<CompetitionPodium> getCompetitionPodium() {
		return competitionPodium;
	}

	/**
	 * Setter of the property <tt>competitionPodium</tt>
	 * @param competitionPodium  The competitionPodium to set.
	 * uml.property  name="competitionPodium"
	 */
	public void setCompetitionPodium(Collection<CompetitionPodium> competitionPodium) {
		this.competitionPodium = competitionPodium;
	}

	/**
	 * uml.property  name="competitionWinner"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.CompetitionWinner"
	 */
	private Collection<CompetitionWinner> competitionWinner;

	/**
	 * Getter of the property <tt>competitionWinner</tt>
	 * @return  Returns the competitionWinner.
	 * uml.property  name="competitionWinner"
	 */
	public Collection<CompetitionWinner> getCompetitionWinner() {
		return competitionWinner;
	}

	/**
	 * Setter of the property <tt>competitionWinner</tt>
	 * @param competitionWinner  The competitionWinner to set.
	 * uml.property  name="competitionWinner"
	 */
	public void setCompetitionWinner(Collection<CompetitionWinner> competitionWinner) {
		this.competitionWinner = competitionWinner;
	}

	@Override
	public boolean hasValidName() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
