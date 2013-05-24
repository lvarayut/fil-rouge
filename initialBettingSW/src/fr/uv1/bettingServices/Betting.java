package fr.uv1.bettingServices;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author prou + mallet <br>
 * <br>
 *         This interface declares all methods that should be provided by a
 *         betting software. <br>
 * 
 */
public interface Betting {

	/**
	 * Subscribe.
	 * 
	 * @param a_name
	 *            the last name of the subscriber.
	 * @param a_firstName
	 *            the first name of the subscriber.
	 * @param a_username
	 *            the username of the subscriber.
	 * @param a_managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if a subscriber exists with the same username.
	 * @throws BadParametersException
	 *             raised if last name, first name or username are invalid.
	 * 
	 * @return password for the new subscriber
	 */
	String subscribe(String a_name, String a_firstName, String a_username,
			String a_birthDay, String a_managerPwd) throws AuthenticationException,
			ExistingSubscriberException, BadParametersException;

	/**
	 * delete a subscriber.
	 * 
	 * @param a_username
	 *            the username of the subscriber.
	 * @param a_managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if username is not registered.
	 * 
	 */
	void unsubscribe(String a_username, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException;

	/**
	 * list subscribers.
	 * 
	 * @param a_managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 * 
	 * @return a list of list of strings:
	 *         <ul>
	 *         <li>subscriber's lastname</li>
	 *         <li>subscriber's firstname</li>
	 *         <li>subscriber's username</li>
	 *         </ul>
	 */
	ArrayList<ArrayList<String>> listSubscribers(String a_managerPwd)
			throws AuthenticationException;

	/**
	 * authenticate manager.
	 * 
	 * @param a_managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 */
	void authenticateMngr(String a_managerPwd) throws AuthenticationException;
	

	/**
	 * change manager's password.
	 * 
	 * @param newPwd
	 *            the new manager's password.
	 * @param currentPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the current manager's password is incorrect.
	 * 
	 * @throws BadParametersException
	 *             raised if the new manager's password is invalid.
	 */
	void changeMngrPwd(String newPwd, String currentPwd)
			throws AuthenticationException, BadParametersException;

	/**
	 * Create a new competitor
	 * 
	 * @param firstname
	 *            Competitor's fist name
	 * @param lastname
	 *            Competitor's last name
	 * @param birthDay
	 *            Competitor's birthday
	 * @param managerPwd
	 *            Manager's password
	 * @return New competitor
	 * @throws AuthenticationException 
	 */
	PCompetitor createCompetitor(String firstname, String lastname,
			Calendar birthDay, String managerPwd)
			throws AuthenticationException;
}
