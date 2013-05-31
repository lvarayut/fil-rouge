package fr.uv1.bettingServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.CompetitorDAO;
import fr.uv1.DAO.PodiumDAO;
import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.DAO.WinnerDAO;
import fr.uv1.database.DBConnection;

/**
 * 
 * @author prou + mallet <br>
 * @version 2.0
 * @since 24/05/2013 <br>
 *        This class implements methods of the interface Betting. <br>
 * <br>
 *        <ul>
 *        <li>manager password validity:
 *        <ul>
 *        <li>only letters and digits are allowed</li>
 *        <li>password size should be at least 8 characters</li>
 *        </ul>
 *        </li>
 *        </ul>
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
	 * uml.property name="robot" uml.associationEnd multiplicity="(0 -1)"
	 * inverse="bettingSoft:fr.uv1.bettingServices.Robot"
	 */
	private Collection<Robot> robot;
	/**
	 * uml.property name="person" uml.associationEnd multiplicity="(0 -1)"
	 * inverse="bettingSoft:fr.uv1.bettingServices.Person"
	 */
	private Collection<Person> person;

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

	/**
	 * Set manager's password
	 * 
	 * @param managerPassword
	 *            A password
	 * @throws BadParametersException
	 *             Manager's password is not valid
	 */
	private void setManagerPassword(String managerPassword)
			throws BadParametersException {
		if (managerPassword == null)
			throw new BadParametersException("manager's password is not valid");
		// The password should be valid
		if (!pv.verify(managerPassword.toCharArray()))
			throw new BadParametersException("manager's password is not valid");
		this.managerPassword = managerPassword;
		System.out.println("The manager's password is changed");
	}

	/**
	 * From Betting interface (non-Javadoc)
	 * 
	 */
	@Override
	public String subscribe(String a_name, String a_firstName,
			String a_birthDay, String a_username, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException,
			BadParametersException {
		System.out.println("lastname" + a_name);
		System.out.println("firstname" + a_firstName);
		System.out.println("username" + a_username);
		System.out.println("birthday" + a_birthDay);
		System.out.println("ManagerPassword" + a_managerPwd);
		String query = "";
		String a_password = "";
		int birthDate = 0;
		int birthMonth = 0;
		int birthYear = 0;
		String[] birth = a_birthDay.split("-");
		birthDate = Integer.parseInt(birth[0]);
		birthMonth = Integer.parseInt(birth[1]);
		birthYear = Integer.parseInt(birth[2]);
		Calendar birthDay = Calendar.getInstance();
		birthDay.set(birthYear, birthMonth, birthDate);
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(a_username);
		if (s != null)
			throw new ExistingSubscriberException(
					"A subscriber with the same username already exists");
		// Creates the new subscriber
		if (verifyAge(birthDay)) {
			s = new Subscriber(a_name, a_firstName, a_username, birthDay);
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
	 * Verify age of subscribers
	 * 
	 * @param birthDay
	 *            Subscriber's birthday
	 * @return Whether age is greater than 18 or not
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

	public boolean verifyDuplicateCompetitors(Collection<Competitor> competitors) {
		boolean duplicate = false;
		PCompetitor[] cTor = new PCompetitor[3];
		Iterator iterator = competitors.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			cTor[i] = (PCompetitor) iterator.next();
			i++;
		}
		for(int j =0;j<cTor.length;j++){
			for (int k=0;k<cTor.length;k++){
				if(cTor[j].getFirstname().equals(cTor[k].getFirstname()) && cTor[j].getLastname().equals(cTor[k].getLastname()) && cTor[j].getBirthdate() == cTor[k].getBirthdate())
					return true;
			}
		}
		return false;
	}

	/**
	 * From Betting interface (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Betting#unsubscribe(java.lang.String,
	 *      java.lang.String)
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
	 * From Betting interface (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Betting#listSubscribers(java.lang.String)
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
			subsData.add(s.getLastname());
			subsData.add(s.getFirstname());
			subsData.add(s.getUsername());

			result.add(subsData);
		}
		return result;
	}

	/**
	 * From Betting interface (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Betting#authenticateMngr(java.lang.String)
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
	 * From Betting interface (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Betting#changeMngrPwd(java.lang.String,
	 *      java.lang.String)
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
	 * Search a subscriber by username
	 * 
	 * @param a_username
	 *            Subscriber's username
	 * @return List of subscribers or null
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
	 * From Betting interface (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Betting#createCompetitor(java.lang.String,
	 *      java.lang.String, java.util.Calendar, java.lang.String)
	 */
	@Override
	public PCompetitor createCompetitor(String firstname, String lastname,
			Calendar birthDay, String managerPwd)
			throws AuthenticationException {
		CompetitorDAO cd = new CompetitorDAO();
		PCompetitor com = new PCompetitor(firstname, lastname, birthDay);
		// Authenticate manager
		authenticateMngr(managerPwd);
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
	 * @param a_competition
	 *            Competition's name
	 * @param competitors
	 *            Collection of competitors
	 * @param a_managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 */
	public void addCompetitor(Competition a_competition,
			Collection<Competitor> competitors, String a_managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// 
		CompetitionDAO cd = new CompetitionDAO();
		try {
			cd.addCompetitor(a_competition, competitors);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add a new competition
	 * 
	 * @param a_competition
	 *            Competition's name
	 * @param a_closingDate
	 *            End date
	 * @param competitors
	 *            Collection of competitors
	 * @param a_managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 * @throws ExistingCompetitionException
	 *             The competition is already existed
	 * @throws BadParametersException
	 *             invalid parameters
	 * @throws CompetitionException
	 *             The end date is invalid
	 */
	public void addCompetition(String a_competition, Calendar a_closingDate,
			Collection<Competitor> competitors, String a_managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			BadParametersException, CompetitionException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Check whether the end date is correct or not
		if (a_closingDate.before(Calendar.getInstance())
				|| competitors.size() < 2
				|| verifyDuplicateCompetitors(competitors)) {
			throw new CompetitionException("Invalide end date");
		}
		// Check whether the competition is existed or not
		CompetitionDAO cd = new CompetitionDAO();
		// Check parameters
		if (a_closingDate == null || competitors == null
				|| a_competition == null || a_managerPwd == null)
			throw new BadParametersException();
		try {
			if (cd.isExistCompetition(a_competition))
				throw new ExistingCompetitionException();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Add new competition table DB
		Competition comTion = new Competition(a_competition,
				Calendar.getInstance(), a_closingDate);

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
	 * List all the competition, shown in console
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
	 *            Subscriber's username
	 * @param numberTokens
	 *            Number of tokens
	 * @param managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 * @throws BadParametersException
	 *             The number of tokens lesser than 0
	 */
	public void creditSubscriber(String username, long numberTokens,
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
	 *            Subscriber's username
	 * @param numberTokens
	 *            Number of tokens
	 * @param managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 * @throws BadParametersException
	 *             The number of tokens lesser than 0
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
	 * Calculate the competitors who are first, second, and third
	 * 
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @param second
	 *            First runner-up
	 * @param third
	 *            Second runner-up
	 * @param managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 */
	public void calculatePodiumWinner(String competition, PCompetitor winner,
			PCompetitor second, PCompetitor third, String managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		PodiumDAO pd = new PodiumDAO();
		try {
			pd.addPodiumWinner(competition, winner, second, third);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Calculate the competitor who is the winner
	 * 
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @param managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is wrong
	 */
	public void calculateWinnerWinner(String competition, PCompetitor winner,
			String managerPwd) throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		WinnerDAO wd = new WinnerDAO();
		try {
			wd.addWinner(competition, winner);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Getter of the property <tt>robot</tt>
	 * 
	 * @return Returns the robot. uml.property name="robot"
	 */
	public Collection<Robot> getRobot() {
		return robot;
	}

	/**
	 * Setter of the property <tt>robot</tt>
	 * 
	 * @param robot
	 *            The robot to set. uml.property name="robot"
	 * @throws BadParametersException
	 *             The robot object cannot be null
	 */
	public void setRobot(Collection<Robot> robot) throws BadParametersException {
		if (robot == null)
			throw new BadParametersException("The robot object cannot be null");
		this.robot = robot;
	}

	/**
	 * Getter of the property <tt>person</tt>
	 * 
	 * @return Returns the person. uml.property name="person"
	 */
	public Collection<Person> getPerson() {
		return person;
	}

	/**
	 * Setter of the property <tt>person</tt>
	 * 
	 * @param person
	 *            The person to set. uml.property name="person"
	 * @throws BadParametersException
	 *             The person object cannot be null
	 */
	public void setPerson(Collection<Person> person)
			throws BadParametersException {
		if (person == null)
			throw new BadParametersException("The person object cannot be null");
		this.person = person;
	}

	/**
	 * uml.property name="competition" uml.associationEnd multiplicity="(0 -1)"
	 * inverse="bettingSoft:fr.uv1.bettingServices.Competition"
	 */
	private Collection<Competition> competition;

	/**
	 * Getter of the property <tt>competition</tt>
	 * 
	 * @return Returns the competition. uml.property name="competition"
	 */
	public Collection<Competition> getCompetition() {
		return competition;
	}

	/**
	 * Setter of the property <tt>competition</tt>
	 * 
	 * @param competition
	 *            The competition to set. uml.property name="competition"
	 * @throws BadParametersException
	 *             The competition object cannot be null
	 */
	public void setCompetition(Collection<Competition> competition)
			throws BadParametersException {
		if (competition == null)
			throw new BadParametersException(
					"The competition object cannot be null");
		this.competition = competition;
	}

	public BettingPasswordsVerifier getPv() {
		return pv;
	}

	/**
	 * @param pv
	 *            BettingPasswordVerifier object
	 * @throws BadParametersException
	 *             "The pv object cannot be null"
	 */
	public void setPv(BettingPasswordsVerifier pv)
			throws BadParametersException {
		if (pv == null)
			throw new BadParametersException("The pv object cannot be null");
		this.pv = pv;
	}

	public Collection<Subscriber> getSubscribers() {
		return subscribers;
	}

	/**
	 * @param subscribers
	 *            Collection of subscribers
	 * @throws BadParametersException
	 *             The subscriber object cannot be null
	 */
	public void setSubscribers(Collection<Subscriber> subscribers)
			throws BadParametersException {
		if (subscribers == null)
			throw new BadParametersException(
					"The subscriber object cannot be null");
		this.subscribers = subscribers;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

}