package fr.uv1.bettingServices;

import java.util.Calendar;
import java.util.Collection;


public class CompetitionPodium extends Competition {

	public CompetitionPodium(String name,Calendar startDate,Calendar endDate) {
		// TODO Auto-generated constructor stub
		super(name,startDate,endDate);
	}

	/**
	 * @uml.property  name="betPodium"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitionPodium:fr.uv1.bettingServices.BetPodium"
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
	 * @uml.property  name="competitor"
	 * @uml.associationEnd  multiplicity="(3 3)" inverse="competitionPodium:fr.uv1.bettingServices.Competitor"
	 */
	private Collection competitor;

	/**
	 * Getter of the property <tt>competitor</tt>
	 * @return  Returns the competitor.
	 * @uml.property  name="competitor"
	 */
	public Collection getCompetitor() {
		return competitor;
	}

	/**
	 * Setter of the property <tt>competitor</tt>
	 * @param competitor  The competitor to set.
	 * @uml.property  name="competitor"
	 */
	public void setCompetitor(Collection competitor) {
		this.competitor = competitor;
	}

}
