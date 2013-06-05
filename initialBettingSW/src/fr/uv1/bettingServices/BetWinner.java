package fr.uv1.bettingServices;

import java.sql.SQLException;

import fr.uv1.DAO.BetPodiumDAO;
import fr.uv1.DAO.BetWinnerDAO;
import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.CompetitorDAO;
import fr.uv1.DAO.PodiumDAO;
import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.DAO.WinnerDAO;

public class BetWinner extends Bet {

	public BetWinner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws SQLException
	 * @throws ExistingSubscriberException 
	 */
	public void betOnWinner(long number_tokens, String a_competition,
			Competitor a_winner, String a_username, String pwdSubs)
			throws AuthenticationException, CompetitionException,
			ExistingCompetitionException, SubscriberException,
			BadParametersException, SQLException, ExistingSubscriberException {
		PCompetitor win = (PCompetitor)a_winner;
		// Authenticate subscriber
		Subscriber.authenticateSubscriber(pwdSubs);
		// Check the existing competition
		if (!Competition.existCompetition(a_competition)) {
			throw new ExistingCompetitionException();
		}
		// check if there is no competitor with the name winner
		if (a_winner.equals(null))
			throw new CompetitionException(
					"Please give names for all competitors");
		// check if the subscriber has enough tokens
		SubscriberDAO subs = new SubscriberDAO();
		if (subs.getNumberOfToken(a_username) < number_tokens)
			throw new SubscriberException("The number of tokens is not enough");
		// Check Parameters
		if (number_tokens < 0)
			throw new BadParametersException();
		BetWinnerDAO bd = new BetWinnerDAO();
		try {
			// Bet Winner
			bd.betWinner(number_tokens, a_competition, win, a_username);
			// The number of tokens of the subscriber is debited.
			BettingSoft bs = new BettingSoft("password");
			bs.debitSubscriber(a_username, number_tokens, "password");
		} catch (SQLException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Settle bets on winner
	 * 
	 * @param a_competition
	 *            Competition's name
	 * @param a_winner
	 *            Winner
	 * @param a_managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is invalid
	 * @throws ExistingCompetitionException
	 *             The competition is not existed
	 * @throws SQLException
	 *             SQL problem
	 * @throws BadParametersException
	 */
	public void settleWinner(String a_competition, Competitor a_winner,
			String a_managerPwd) throws AuthenticationException,
			ExistingCompetitionException, CompetitionException,
			BadParametersException, SQLException {
		PCompetitor win = (PCompetitor)a_winner;
		// Authenticate manager
		BettingSoft bs = new BettingSoft(a_managerPwd);
		bs.authenticateMngr(a_managerPwd);
		CompetitionDAO cTionD = new CompetitionDAO();
		// Exist Competition
		if (!cTionD.isExistCompetition(a_competition))
			throw new ExistingCompetitionException(
					"The competition doesn't exist");
		// Exist Competitor
		CompetitorDAO cTorD = new CompetitorDAO();
		if (!cTorD.isExistCompetitor(win.getId()))
			throw new CompetitionException("The competitor doesn't exist");
		WinnerDAO wd = new WinnerDAO();
		wd.settleWinnerToSubscriber(a_competition, win);
	}

	/**
	 * uml.property name="competitor" uml.associationEnd multiplicity="(1 1)"
	 * inverse="betWinner:fr.uv1.bettingServices.Competitor"
	 */
	private PCompetitor competitor;

	/**
	 * Getter of the property <tt>competitor</tt>
	 * 
	 * @return Returns the competitor. uml.property name="competitor"
	 */
	public PCompetitor getCompetitor() {
		return competitor;
	}

	/**
	 * Setter of the property <tt>competitor</tt>
	 * 
	 * @param competitor
	 *            The competitor to set. uml.property name="competitor"
	 */
	public void setCompetitor(PCompetitor competitor) {
		this.competitor = competitor;
	}

	/**
	 * uml.property name="competitionWinner" uml.associationEnd
	 * multiplicity="(1 1)"
	 * inverse="betWinner:fr.uv1.bettingServices.CompetitionWinner"
	 */
	private CompetitionWinner competitionWinner;

	/**
	 * Getter of the property <tt>competitionWinner</tt>
	 * 
	 * @return Returns the competitionWinner. uml.property
	 *         name="competitionWinner"
	 */
	public CompetitionWinner getCompetitionWinner() {
		return competitionWinner;
	}

	/**
	 * Setter of the property <tt>competitionWinner</tt>
	 * 
	 * @param competitionWinner
	 *            The competitionWinner to set. uml.property
	 *            name="competitionWinner"
	 */
	public void setCompetitionWinner(CompetitionWinner competitionWinner) {
		this.competitionWinner = competitionWinner;
	}

}
