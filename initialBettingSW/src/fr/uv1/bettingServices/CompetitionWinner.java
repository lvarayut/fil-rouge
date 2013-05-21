package fr.uv1.bettingServices;

import java.util.Calendar;
import java.util.Collection;


public class CompetitionWinner extends Competition {

	public CompetitionWinner(String name,Calendar startDate,Calendar endDate) {
		// TODO Auto-generated constructor stub
		super(name,startDate,endDate);
	}

	/**
	 * @uml.property  name="betWinner"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competitionWinner:fr.uv1.bettingServices.BetWinner"
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
	 * @uml.property  name="competitor"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="competitionWinner:fr.uv1.bettingServices.Competitor"
	 */
	private PCompetitor competitor;

	/**
	 * Getter of the property <tt>competitor</tt>
	 * @return  Returns the competitor.
	 * @uml.property  name="competitor"
	 */
	public PCompetitor getCompetitor() {
		return competitor;
	}

	/**
	 * Setter of the property <tt>competitor</tt>
	 * @param competitor  The competitor to set.
	 * @uml.property  name="competitor"
	 */
	public void setCompetitor(PCompetitor competitor) {
		this.competitor = competitor;
	}

}
