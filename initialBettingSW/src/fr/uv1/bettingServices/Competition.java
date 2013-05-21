package fr.uv1.bettingServices;

import java.util.Calendar;
import java.util.Collection;


public class Competition {

	private String name;
	private Calendar startDate;
	private Calendar endDate;
	public Competition(String name,Calendar startDate,Calendar endDate) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	/**
	 * @uml.property  name="bettingSoft"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="competition:fr.uv1.bettingServices.BettingSoft"
	 */
	private BettingSoft bettingSoft;
	/**
	 * Getter of the property <tt>bettingSoft</tt>
	 * @return  Returns the bettingSoft.
	 * @uml.property  name="bettingSoft"
	 */
	public BettingSoft getBettingSoft() {
		return bettingSoft;
	}
	/**
	 * Setter of the property <tt>bettingSoft</tt>
	 * @param bettingSoft  The bettingSoft to set.
	 * @uml.property  name="bettingSoft"
	 */
	public void setBettingSoft(BettingSoft bettingSoft) {
		this.bettingSoft = bettingSoft;
	}
	/**
	 * @uml.property  name="bet"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="competition:fr.uv1.bettingServices.Bet"
	 */
	private Collection bet;
	/**
	 * Getter of the property <tt>bet</tt>
	 * @return  Returns the bet.
	 * @uml.property  name="bet"
	 */
	public Collection getBet() {
		return bet;
	}
	/**
	 * Setter of the property <tt>bet</tt>
	 * @param bet  The bet to set.
	 * @uml.property  name="bet"
	 */
	public void setBet(Collection bet) {
		this.bet = bet;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

}
