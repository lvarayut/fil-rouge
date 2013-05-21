package fr.uv1.bettingServices;

import java.util.Calendar;
import java.util.Collection;


public class PCompetitor extends Person  implements Competitor{
	private int id;
	public PCompetitor(String firstname,String lastname,String username,Calendar birthDay) {
		// TODO Auto-generated constructor stub
		super(firstname, lastname,birthDay);
	}

	/**
	 * @uml.property  name="betPodium"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.BetPodium"
	 */
	private Collection betPodium;

	/**
	 * Getter of the property <tt>betPodium</tt>
	 * @return  Returns the betPodium.
	 * @uml.property  name="betPodium"
	 */
	public Collection getBetPodium() {
		return betPodium;
	}

	/**
	 * Setter of the property <tt>betPodium</tt>
	 * @param betPodium  The betPodium to set.
	 * @uml.property  name="betPodium"
	 */
	public void setBetPodium(Collection betPodium) {
		this.betPodium = betPodium;
	}

	/**
	 * @uml.property  name="betWinner"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.BetWinner"
	 */
	private Collection betWinner;

	/**
	 * Getter of the property <tt>betWinner</tt>
	 * @return  Returns the betWinner.
	 * @uml.property  name="betWinner"
	 */
	public Collection getBetWinner() {
		return betWinner;
	}

	/**
	 * Setter of the property <tt>betWinner</tt>
	 * @param betWinner  The betWinner to set.
	 * @uml.property  name="betWinner"
	 */
	public void setBetWinner(Collection betWinner) {
		this.betWinner = betWinner;
	}

	/**
	 * @uml.property  name="competitionPodium"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.CompetitionPodium"
	 */
	private Collection competitionPodium;

	/**
	 * Getter of the property <tt>competitionPodium</tt>
	 * @return  Returns the competitionPodium.
	 * @uml.property  name="competitionPodium"
	 */
	public Collection getCompetitionPodium() {
		return competitionPodium;
	}

	/**
	 * Setter of the property <tt>competitionPodium</tt>
	 * @param competitionPodium  The competitionPodium to set.
	 * @uml.property  name="competitionPodium"
	 */
	public void setCompetitionPodium(Collection competitionPodium) {
		this.competitionPodium = competitionPodium;
	}

	/**
	 * @uml.property  name="competitionWinner"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitor:fr.uv1.bettingServices.CompetitionWinner"
	 */
	private Collection competitionWinner;

	/**
	 * Getter of the property <tt>competitionWinner</tt>
	 * @return  Returns the competitionWinner.
	 * @uml.property  name="competitionWinner"
	 */
	public Collection getCompetitionWinner() {
		return competitionWinner;
	}

	/**
	 * Setter of the property <tt>competitionWinner</tt>
	 * @param competitionWinner  The competitionWinner to set.
	 * @uml.property  name="competitionWinner"
	 */
	public void setCompetitionWinner(Collection competitionWinner) {
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
