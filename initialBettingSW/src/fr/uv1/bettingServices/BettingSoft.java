package fr.uv1.bettingServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import sun.security.util.Password;

import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.CompetitorDAO;
import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.database.DBConnection;

/**
 * 
 * @author prou + mallet <br>
 * <br>
 *         This class implements methods of the interface Betting. <br>
 * <br>
 *         <ul>
 *         <li>manager password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 */

public class BettingSoft implements Betting {

	/*
	 * Manager password
	 */
	private String managerPassword;

	/*
	 * Password verifier
	 */
	private BettingPasswordsVerifier pv;

	/*
	 * Subscribers of the betting software
	 */
	private Collection<Subscriber> subscribers;
	/**
	 * @uml.property name="robot"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="bettingSoft:fr.uv1.bettingServices.Robot"
	 */
	private Collection robot;
	/**
	 * @uml.property name="person"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="bettingSoft:fr.uv1.bettingServices.Person"
	 */
	private Collection person;

	/**
	 * constructor of BettingSoft
	 * 
	 * @param a_managerPwd
	 *            manager password.
	 * 
	 * @throws BadParametersException
	 *             raised if a_managerPwd is incorrect.
	 */
	public BettingSoft(String a_managerPwd) throws BadParametersException {
		// Create the verifier
		pv = new BettingPasswordsVerifier();

		// The password should be valid
		setManagerPassword(a_managerPwd);
		this.subscribers = new ArrayList<Subscriber>();
	}

	private void setManagerPassword(String managerPassword)
			throws BadParametersException {
		if (managerPassword == null)
			throw new BadParametersException("manager's password not valid");
		// The password should be valid
		if (!pv.verify(managerPassword.toCharArray()))
			throw new BadParametersException("manager's password not valid");
		this.managerPassword = managerPassword;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public String subscribe(String a_name, String a_firstName,
			String a_username, Calendar a_birthDay, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException,
			BadParametersException {
		String query = "";
		String a_password = "";
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(a_username);
		if (s != null)
			throw new ExistingSubscriberException(
					"A subscriber with the same username already exists");
		// Creates the new subscriber
		if (verifyAge(a_birthDay)) {
			s = new Subscriber(a_name, a_firstName, a_username, a_birthDay);
			// Add it to the collection of subscribers
			subscribers.add(s);
			a_password = s.getPassword();
			// Connect to data base
			SubscriberDAO sd = new SubscriberDAO();
			try {
				sd.addSubscriber(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return a_password;
	}

	/**
	 * verify age of subscribers
	 */
	public boolean verifyAge(Calendar birthDay) {
		Calendar current = Calendar.getInstance();

		int age = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		if (current.get(Calendar.MONTH) < birthDay.get(Calendar.MONTH))
			age--;
		// case of equal month
		else if (current.DAY_OF_MONTH < birthDay.DAY_OF_MONTH)
			age--;
		return age >= 18;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public void unsubscribe(String a_username, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(a_username);
		if (s != null)
			subscribers.remove(s); // remove it
		else
			throw new ExistingSubscriberException("Subscriber does not exist");
	}

	/**
	 * From Betting interface
	 */
	@Override
	public ArrayList<ArrayList<String>> listSubscribers(String a_managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Calculate the list of subscribers
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> subsData = new ArrayList<String>();
		for (Subscriber s : subscribers) {
			subsData.add(s.getLastName());
			subsData.add(s.getFirstName());
			subsData.add(s.getUsername());

			result.add(subsData);
		}
		return result;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public void authenticateMngr(String a_managerPwd)
			throws AuthenticationException {
		if (a_managerPwd == null)
			throw new AuthenticationException("invalid manager's password");

		if (!this.managerPassword.equals(a_managerPwd))
			throw new AuthenticationException("incorrect manager's password");
	}

	
	/**
	 * From Betting interface
	 */
	@Override
	public void changeMngrPwd(String newPwd, String currentPwd)
			throws AuthenticationException, BadParametersException {
		// Authenticate manager
		authenticateMngr(currentPwd);
		// Change password if valid
		setManagerPassword(newPwd);
	}

	/**
	 * search a subscriber by username
	 * 
	 * @param a_username
	 *            the username of the subscriber.
	 * 
	 * @return the found subscriber or null
	 */
	private Subscriber searchSubscriberByUsername(String a_username) {
		if (a_username == null)
			return null;
		for (Subscriber s : subscribers) {
			if (s.hasUsername(a_username))
				return s;
		}
		return null;
	}

	/**
	 * Create a new competitor
	 */
	public PCompetitor createCompetitor(String username, String firstname,
			String lastname, Calendar birthDay) {
		CompetitorDAO cd = new CompetitorDAO();
		PCompetitor com = new PCompetitor(firstname, lastname, username,
				birthDay);
		try {
			com = cd.createCompetitor(com);
		} catch (SQLException | ExistingCompetitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return com;
	}

	/**
	 * Add competitors to participate competitions
	 * 
	 * @throws AuthenticationException
	 */
	public void addCompetitor(Competition a_competition,
			Collection competitors, String a_managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		CompetitionDAO cd = new CompetitionDAO();
		try {
			cd.addCompetitor(a_competition, competitors);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add new competition
	 */
	public void addCompetition(String a_competition, Calendar a_closingDate,
			Collection competitors, String a_managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			CompetitionException, BadParametersException {
		// Check whether the end date is correct or not
		if (a_closingDate.before(Calendar.getInstance())) {
			throw new BadParametersException("Invalide end date");
		}
		// Add new competition table DB
		Competition comTion = new Competition(a_competition,
				Calendar.getInstance(), a_closingDate);
		CompetitionDAO cd = new CompetitionDAO();
		try {
			cd.addCompetition(comTion);
			// Add new competitor into participate table DB
			addCompetitor(comTion, competitors, a_managerPwd);
			// Verify whether the competitors exist in Competitor DB or not
		} catch (Exception e) {// SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * List all the competition
	 */
	public void listCompetitions() {
		CompetitionDAO cd = new CompetitionDAO();
		Collection allCTion = new ArrayList<PCompetitor>();
		try {
			allCTion = cd.listAllCompetition();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator iterator = allCTion.iterator();
		while (iterator.hasNext()) {
			// Month's index starts from 0
			Competition cTion = (Competition) iterator.next();
			System.out.println("The competition's name " + cTion.getName());
			System.out.println("The competition's start date "
					+ cTion.getStartDate().get(Calendar.DAY_OF_MONTH) + "/"
					+ (cTion.getStartDate().get(Calendar.MONTH) + 1) + "/"
					+ cTion.getStartDate().get(Calendar.YEAR));
			System.out.println("The competition's end date "
					+ cTion.getEndDate().get(Calendar.DAY_OF_MONTH) + "/"
					+ (cTion.getEndDate().get(Calendar.MONTH) + 1) + "/"
					+ cTion.getEndDate().get(Calendar.YEAR));
		}
	}

	/**
	 * Credit number of tokens of a subscriber.
	 * 
	 * @param username
	 * @param numberTokens
	 * @param managerPwd
	 * @throws AuthenticationException
	 * @throws BadParametersException
	 */
	public void creditCompetitor(String username, long numberTokens,
			String managerPwd) throws AuthenticationException,
			BadParametersException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		// Verify number of tokens
		if (numberTokens <= 0)
			throw new BadParametersException(
					"The number of tokens should be greater than 0");
		SubscriberDAO sd = new SubscriberDAO();
		try {
			sd.addTokens(username, numberTokens);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Debit a subscriber account with a number of tokens.
	 * 
	 * @param username
	 * @param numberTokens
	 * @param managerPwd
	 * @throws AuthenticationException
	 * @throws BadParametersException
	 */
	public void debitSubscriber(String username, long numberTokens,
			String managerPwd) throws AuthenticationException,
			BadParametersException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		// Verify number of tokens
		if (numberTokens > 0)
			throw new BadParametersException(
					"The number of tokens should be lesser than 0");
		SubscriberDAO sd = new SubscriberDAO();
		try {
			sd.removeTokens(username, numberTokens);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
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
	 * Getter of the property <tt>person</tt>
	 * 
	 * @return Returns the person.
	 * @uml.property name="person"
	 */
	public Collection getPerson() {
		return person;
	}

	/**
	 * Setter of the property <tt>person</tt>
	 * 
	 * @param person
	 *            The person to set.
	 * @uml.property name="person"
	 */
	public void setPerson(Collection person) {
		this.person = person;
	}

	/**
	 * @uml.property name="competition"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="bettingSoft:fr.uv1.bettingServices.Competition"
	 */
	private Collection competition;

	/**
	 * Getter of the property <tt>competition</tt>
	 * 
	 * @return Returns the competition.
	 * @uml.property name="competition"
	 */
	public Collection getCompetition() {
		return competition;
	}

	/**
	 * Setter of the property <tt>competition</tt>
	 * 
	 * @param competition
	 *            The competition to set.
	 * @uml.property name="competition"
	 */
	public void setCompetition(Collection competition) {
		this.competition = competition;
	}

}