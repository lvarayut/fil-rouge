package fr.uv1.bettingServices;

import java.util.Collection;


public abstract class Bet {
	
	private int numberTokenBet;

	public Bet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="competition"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="bet:fr.uv1.bettingServices.Competition"
	 */
	private Collection competition;

	/**
	 * Getter of the property <tt>competition</tt>
	 * @return  Returns the competition.
	 * @uml.property  name="competition"
	 */
	public Collection getCompetition() {
		return competition;
	}

	/**
	 * Setter of the property <tt>competition</tt>
	 * @param competition  The competition to set.
	 * @uml.property  name="competition"
	 */
	public void setCompetition(Collection competition) {
		this.competition = competition;
	}

	/**
	 * @uml.property  name="robot"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="bet:fr.uv1.bettingServices.Robot"
	 */
	private Robot robot;

	/**
	 * Getter of the property <tt>robot</tt>
	 * @return  Returns the robot.
	 * @uml.property  name="robot"
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * Setter of the property <tt>robot</tt>
	 * @param robot  The robot to set.
	 * @uml.property  name="robot"
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	/**
	 * @uml.property  name="subscriber"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="bet:fr.uv1.bettingServices.Subscriber"
	 */
	private Subscriber subscriber;

	/**
	 * Getter of the property <tt>subscriber</tt>
	 * @return  Returns the subscriber.
	 * @uml.property  name="subscriber"
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * Setter of the property <tt>subscriber</tt>
	 * @param subscriber  The subscriber to set.
	 * @uml.property  name="subscriber"
	 */
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}
