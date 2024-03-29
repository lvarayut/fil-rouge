package fr.uv1.bettingServices;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;

import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.utils.*;
import java.util.Collection;

/**
 * 
 * @author prou, segarra<br>
 * @version 2.0
 * @since 24/05/2013
 *  <br>
 *        This class represents a subscriber for a betting application. <br>
 * <br>
 *        The constructor of the class creates a password for the subscriber. <br>
 *        <ul>
 *        <li>subscriber's password validity:
 *        <ul>
 *        <li>only letters and digits are allowed</li>
 *        <li>password size should be at least 8 characters</li>
 *        </ul>
 *        </li>
 *        <li>for the username validity:
 *        <ul>
 *        <li>only letters and digits are allowed</li>
 *        <li>size should be at least 4 characters</li>
 *        </ul>
 *        </li>
 *        </ul>
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
		this.setUsername(a_username);
		// this.setLastname(a_name);
		// this.setFirstname(a_firstName);
		// this.setUsername(a_username);
		this.pv = new BettingPasswordsVerifier();
		// Generate password
		RandPass rp = new RandPass();
		rp.addVerifier(pv);
		this.setPassword(new RandPass().getPass(Constraints.LONG_PWD));
	}

	public String convertBirthdayToString(){
		String strBD = "";
		strBD += this.getBirthdate().get(Calendar.YEAR)+"-";
		strBD += this.getBirthdate().get(Calendar.MONTH)+"-";
		strBD += this.getBirthdate().get(Calendar.DAY_OF_MONTH);
		return strBD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws BadParametersException {
		if (password == null)
			throw new BadParametersException("password is not valid");
		if (!pv.verify(password.toCharArray()))
			throw new BadParametersException("password is not valid");
		this.password = password;
	}

	// /**
	// */
	// public void creditSubscriber(String a_username, long number_tokens,
	// String a_managerPwd) throws AuthenticationException,
	// ExistingSubscriberException, BadParametersException {
	// }
	//
	// /**
	// */
	// public void debitSubscriber(String a_username, long number_tokens,
	// String a_managerPwd) throws AuthenticationException,
	// ExistingSubscriberException, SubscriberException,
	// BadParametersException {
	// }



	/**
	 * uml.property name="robot"
	 * uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="subscriber:fr.uv1.bettingServices.Robot"
	 */
	private Collection<Robot> robot;

	/**
	 * Getter of the property <tt>robot</tt>
	 * 
	 * @return Returns the robot.
	 * uml.property name="robot"
	 */
	public Collection<Robot> getRobot() {
		return robot;
	}

	/**
	 * Setter of the property <tt>robot</tt>
	 * 
	 * @param robot
	 *            The robot to set.
	 * uml.property name="robot"
	 * @throws BadParametersException 
	 */
	public void setRobot(Collection<Robot> robot) throws BadParametersException {
		if(robot==null)
			throw new BadParametersException("The robot object is null");
		this.robot = robot;
	}

	/**
	 * uml.property name="bet"
	 * uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="subscriber:fr.uv1.bettingServices.Bet"
	 */
	private Collection<Bet> bet;

	/**
	 * Getter of the property <tt>bet</tt>
	 * 
	 * @return Returns the bet.
	 * uml.property name="bet"
	 */
	public Collection<Bet> getBet() {
		return bet;
	}

	/**
	 * Setter of the property <tt>bet</tt>
	 * 
	 * @param bet
	 *            The bet to set.
	 * uml.property name="bet"
	 * @throws BadParametersException 
	 */
	public void setBet(Collection<Bet> bet) throws BadParametersException {
		if(bet == null)
				throw new BadParametersException("Bet should not be null");

		this.bet = bet;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		// Verify negative value
		if(token <0)
			try {
				throw new BadParametersException("The number of tokens should be geather than 0");
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	

	/**
	 * Authentication by using subsciber's password
	 * 
	 * @param a_subscriberPwd
	 *            Subscriber's password
	 * @throws AuthenticationException
	 *             The subscriber's password is invalid
	 * @throws BadParametersException
	 * @throws SQLException
	 */
	public static void  authenticateSubscriber(String a_subscriberPwd)
			throws AuthenticationException, SQLException,
			BadParametersException {
		if (a_subscriberPwd == null)
			throw new AuthenticationException("invalid subscriber's password");
		SubscriberDAO bpd = new SubscriberDAO();
		
		boolean found = false;
		Collection<Subscriber> sub = bpd.listAllSubscriber();
		Iterator iterator = sub.iterator();
		while (iterator.hasNext()) {
			Subscriber s = (Subscriber) iterator.next();
			if (s.getPassword().equals(a_subscriberPwd))
				found = true;
		}
		if (!found)
			throw new AuthenticationException("incorrect subscriber's password");
	}

	public static boolean existSubscriber(String username) throws SQLException{
		SubscriberDAO subs = new SubscriberDAO();
		return subs.isExistSubscriber(username);
		
	}
}
