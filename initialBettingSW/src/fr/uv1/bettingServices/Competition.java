package fr.uv1.bettingServices;

import java.util.Calendar;
import java.util.Collection;
/**
 * This class is used to create t
 * @author Rokhaya and Varayut
 * @version 2.0
 * @since 24/05/2013
 *
 */

public class Competition {

	private String name;
	private Calendar startDate;
	private Calendar endDate;
	public Competition(String name,Calendar startDate,Calendar endDate) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		try {
			this.setStartDate(startDate);
			this.setEndDate(endDate);
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * uml.property  name="bettingSoft"
	 * uml.associationEnd  multiplicity="(1 1)" inverse="competition:fr.uv1.bettingServices.BettingSoft"
	 */
	private BettingSoft bettingSoft;
	/**
	 * Getter of the property <tt>bettingSoft</tt>
	 * @return  Returns the bettingSoft.
	 * uml.property  name="bettingSoft"
	 */
	public BettingSoft getBettingSoft() {
		return bettingSoft;
	}
	/**
	 * Setter of the property <tt>bettingSoft</tt>
	 * @param bettingSoft  The bettingSoft to set.
	 * uml.property  name="bettingSoft"
	 * @throws BadParametersException The betingSoft object cannot be null
	 */
	public void setBettingSoft(BettingSoft bettingSoft) throws BadParametersException {
		if(bet == null)
			throw new BadParametersException("The betingSoft object cannot be null");
		this.bettingSoft = bettingSoft;
	}
	/**
	 * uml.property  name="bet"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="competition:fr.uv1.bettingServices.Bet"
	 */
	private Collection<Bet> bet;
	/**
	 * Getter of the property <tt>bet</tt>
	 * @return  Returns the bet.
	 * uml.property  name="bet"
	 */
	public Collection<Bet> getBet() {
		return bet;
	}
	/**
	 * Setter of the property <tt>bet</tt>
	 * @param bet  The bet to set.
	 * uml.property  name="bet"
	 * @throws BadParametersException The bet object cannot be null
	 */
	public void setBet(Collection<Bet> bet) throws BadParametersException {
		if(bet == null)
			throw new BadParametersException("The bet object cannot be null");
		this.bet = bet;
	}
	/**
	 * @return Competition's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Competition's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Calendar object
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate Calendar object
	 * @throws BadParametersException The startDate object cannot be null
	 */
	public void setStartDate(Calendar startDate) throws BadParametersException {
		if(startDate == null)
			throw new BadParametersException("The startDate object cannot be null");
		this.startDate = startDate;
	}
	/**
	 * @return Calendar object
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate Calendar object
	 * @throws BadParametersException
	 */
	public void setEndDate(Calendar endDate) throws BadParametersException {
		if(endDate == null)
			throw new BadParametersException("The endDate object cannot be null");
		this.endDate = endDate;
	}

}
