package fr.uv1.bettingServices;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;

import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.utils.*;
import java.util.Collection;

/**
 * 
 * @author prou, segarra<br>
 * <br>
 *         This class represents a subscriber for a betting application. <br>
 * <br>
 *         The constructor of the class creates a password for the subscriber. <br>
 *         <ul>
 *         <li>subscriber's password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         <li>for the username validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>size should be at least 4 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 * 
 */
public class Subscriber extends Person implements Serializable {
	private static final long serialVersionUID = 6050931528781005411L;
	/*
	 * Minimal size for a subscriber's username
	 */
	private static final int LONG_USERNAME = 4;

	// Subscriber's password verifier
	BettingPasswordsVerifier pv;

	private String password, username;
	private int token;

	/*
	 * the constructor calculates a password for the subscriber. No test on the
	 * validity of names
	 */
	public Subscriber(String a_lastname, String a_firstName, String a_username,
			Calendar a_birthDay) throws BadParametersException {
		super(a_firstName, a_lastname, a_birthDay);
		this.username = a_username;
		// this.setLastname(a_name);
		// this.setFirstname(a_firstName);
		// this.setUsername(a_username);
		this.pv = new BettingPasswordsVerifier();
		// Generate password
		RandPass rp = new RandPass();
		rp.addVerifier(pv);
		this.setPassword(new RandPass().getPass(Constraints.LONG_PWD));
	}
	/**
	 * Authentication by using subsciber's password
	 */

	public void authenticateSubscriber(String a_subscriberPwd)
			throws AuthenticationException {
		if (a_subscriberPwd == null)
			throw new AuthenticationException("invalid subscriber's password");

		if (!this.password.equals(a_subscriberPwd))
			throw new AuthenticationException("incorrect subscriber's password");
	}
	public String getPassword() {
		return password;
	}

	private void setPassword(String password) throws BadParametersException {
		if (password == null)
			throw new BadParametersException("password is not valid");
		if (!pv.verify(password.toCharArray()))
			throw new BadParametersException("password is not valid");
		this.password = password;
	}

//	/**
//		 */
//	public void creditSubscriber(String a_username, long number_tokens,
//			String a_managerPwd) throws AuthenticationException,
//			ExistingSubscriberException, BadParametersException {
//	}
//
//	/**
//			 */
//	public void debitSubscriber(String a_username, long number_tokens,
//			String a_managerPwd) throws AuthenticationException,
//			ExistingSubscriberException, SubscriberException,
//			BadParametersException {
//	}

	
	/**
	 * Bet on podium 
	 * The number of tokens of the subscriber is debited.
	 * @param numberTokens
	 * @param competition
	 * @param winner
	 * @param second
	 * @param third
	 * @param username
	 * @param pwdSubs
	 * @throws AuthenticationException 
	 */
	public void betOnPodium(long numberTokens, String competition,
			PCompetitor winner, PCompetitor second, PCompetitor third,
			String username, String pwdSubs) throws AuthenticationException {
		// Authenticate manager
		authenticateSubscriber(password);
		SubscriberDAO sd = new SubscriberDAO();
		try {
			// Bet Podium
			sd.betPodium(numberTokens,competition,winner,second,third,username);
			// The number of tokens of the subscriber is debited.
			BettingSoft bs = new BettingSoft("password");
			bs.debitSubscriber(username, -numberTokens, "password");
		} catch (SQLException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @uml.property name="robot"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="subscriber:fr.uv1.bettingServices.Robot"
	 */
	private Collection robot;

	/**
	 * Getter of the property <tt>robot</tt>
	 * 
	 * @return Returns the robot.
	 * @uml.property name="robot"
	 */
	public Collection getRobot() {
		return robot;
	}

	/**
	 * Setter of the property <tt>robot</tt>
	 * 
	 * @param robot
	 *            The robot to set.
	 * @uml.property name="robot"
	 */
	public void setRobot(Collection robot) {
		this.robot = robot;
	}

	/**
	 * @uml.property name="bet"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="subscriber:fr.uv1.bettingServices.Bet"
	 */
	private Collection bet;

	/**
	 * Getter of the property <tt>bet</tt>
	 * 
	 * @return Returns the bet.
	 * @uml.property name="bet"
	 */
	public Collection getBet() {
		return bet;
	}

	/**
	 * Setter of the property <tt>bet</tt>
	 * 
	 * @param bet
	 *            The bet to set.
	 * @uml.property name="bet"
	 */
	public void setBet(Collection bet) {
		this.bet = bet;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * check the validity of a string for a subscriber username, letters and
	 * digits are allowed. username length should at least be LONG_USERNAME
	 * characters
	 * 
	 * @param a_username
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringUsername(String a_username)
			throws BadParametersException {
		if (a_username == null)
			throw new BadParametersException("username not instantiated");

		if (a_username.length() < LONG_USERNAME)
			throw new BadParametersException("username length less than "
					+ LONG_USERNAME + "characters");
		for (int i = 0; i < a_username.length(); i++) {
			char c = a_username.charAt(i);
			if (!Character.isDigit(c) && !Character.isLetter(c))
				throw new BadParametersException("the username " + a_username
						+ " contains other characters as letters and digits");
		}
	}

	public void setUsername(String username) throws BadParametersException {
		if (getLastname() == null)
			throw new BadParametersException("username is not valid");
		checkStringUsername(username);
		this.username = username;
	}

	/*
	 * check if this subscriber has the username of the parameter
	 * 
	 * @param username the username to check
	 * 
	 * @return true if this username is the same as the parameter false
	 * otherwise
	 */
	public boolean hasUsername(String username) {
		if (username == null)
			return false;
		return this.username.equals(username);
	}

	/*
	 * Two subscribers are equal if they have the same username
	 */
	@Override
	public boolean equals(Object an_object) {
		if (!(an_object instanceof Subscriber))
			return false;
		Subscriber s = (Subscriber) an_object;
		return this.username.equals(s.getUsername());
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		return " " + getFirstname() + " " + getLastname() + " " + username;
	}

}
