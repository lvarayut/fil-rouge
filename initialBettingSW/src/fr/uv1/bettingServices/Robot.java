package fr.uv1.bettingServices;

import java.util.Collection;


public class Robot {
	private String name;

	public Robot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * uml.property  name="bettingSoft"
	 * uml.associationEnd  multiplicity="(1 1)" inverse="robot:fr.uv1.bettingServices.BettingSoft"
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
	 */
	public void setBettingSoft(BettingSoft bettingSoft) {
		this.bettingSoft = bettingSoft;
	}

	/**
	 * uml.property  name="subscriber"
	 * uml.associationEnd  multiplicity="(1 1)" inverse="robot:fr.uv1.bettingServices.Subscriber"
	 */
	private Subscriber subscriber;

	/**
	 * Getter of the property <tt>subscriber</tt>
	 * @return  Returns the subscriber.
	 * uml.property  name="subscriber"
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * Setter of the property <tt>subscriber</tt>
	 * @param subscriber  The subscriber to set.
	 * uml.property  name="subscriber"
	 */
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * uml.property  name="bet"
	 * uml.associationEnd  multiplicity="(0 -1)" inverse="robot:fr.uv1.bettingServices.Bet"
	 */
	private Collection bet;

	/**
	 * Getter of the property <tt>bet</tt>
	 * @return  Returns the bet.
	 * uml.property  name="bet"
	 */
	public Collection getBet() {
		return bet;
	}

	/**
	 * Setter of the property <tt>bet</tt>
	 * @param bet  The bet to set.
	 * uml.property  name="bet"
	 */
	public void setBet(Collection bet) {
		this.bet = bet;
	}

}
