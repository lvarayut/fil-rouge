package fr.uv1.bettingServices;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import fr.uv1.DAO.BetPodiumDAO;
import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.CompetitorDAO;
import fr.uv1.DAO.PodiumDAO;
import fr.uv1.DAO.SubscriberDAO;

public class BetPodium extends Bet {

	public BetPodium() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Settle bets on podium
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
	 *             The manager's password is invalid
	 * @throws ExistingCompetitionException
	 *             The competition is not existed
	 * @throws SQLException
	 *             SQL problem
	 * @throws BadParametersException
	 * @throws CompetitionException
	 */
	public void settlePodium(String competition, PCompetitor winner,
			PCompetitor second, PCompetitor third, String managerPwd)
			throws AuthenticationException, SQLException,
			ExistingCompetitionException, BadParametersException,
			CompetitionException {
		// Authenticate manager
		BettingSoft bs = new BettingSoft(managerPwd);
		bs.authenticateMngr(managerPwd);
		CompetitionDAO cd = new CompetitionDAO();
		// Exist Competition
		if (!cd.isExistCompetition(competition))
			throw new ExistingCompetitionException(
					"The competition doesn't exist");
		// Exist Competitor
		CompetitorDAO cTorD = new CompetitorDAO();
		if (!cTorD.isExistCompetitor(winner.getId())
				|| !cTorD.isExistCompetitor(second.getId())
				|| !cTorD.isExistCompetitor(third.getId()))
			throw new CompetitionException("The competitor doesn't exist");
		PodiumDAO pd = new PodiumDAO();
		pd.settlePodiumToSubscriber(competition, winner, second, third);

	}

	/**
	 * Bet on podium The number of tokens of the subscriber is debited.
	 * 
	 * @param numberTokens
	 *            Number of tokens
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @param second
	 *            First runner-up
	 * @param third
	 *            Second runner-up
	 * @param username
	 *            Subscriber's username
	 * @param pwdSubs
	 *            Subscriber's password
	 * @throws AuthenticationException
	 *             The subscriber's password is invalid
	 * @throws BadParametersException
	 * @throws SQLException
	 */
	public void betOnPodium(long numberTokens, String competition,
			PCompetitor winner, PCompetitor second, PCompetitor third,
			String username, String pwdSubs) throws AuthenticationException,
			SQLException, BadParametersException {
		
		// Authenticate subscriber
		Subscriber.authenticateSubscriber(pwdSubs);
		
		// check if the competition already exist or not
		Competition.existCompetition(competition);
		BetPodiumDAO bd = new BetPodiumDAO();
		try {
			// Bet Podium
			bd.betPodium(numberTokens, competition, winner, second, third,
					username);
			// The number of tokens of the subscriber is debited.
			BettingSoft bs = new BettingSoft("password");
			bs.debitSubscriber(username, -numberTokens, "password");
		} catch (SQLException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * uml.property name="competitor" uml.associationEnd multiplicity="(3 3)"
	 * inverse="betPodium:fr.uv1.bettingServices.Competitor"
	 */
	private Collection<Competitor> competitor;

	/**
	 * Getter of the property <tt>competitor</tt>
	 * 
	 * @return Returns the competitor. uml.property name="competitor"
	 */
	public Collection<Competitor> getCompetitor() {
		return competitor;
	}

	/**
	 * Setter of the property <tt>competitor</tt>
	 * 
	 * @param competitor
	 *            The competitor to set. uml.property name="competitor"
	 */
	public void setCompetitor(Collection<Competitor> competitor) {
		this.competitor = competitor;
	}

	/**
	 * uml.property name="competitionPodium" uml.associationEnd
	 * multiplicity="(1 1)"
	 * inverse="betPodium:fr.uv1.bettingServices.CompetitionPodium"
	 */
	private CompetitionPodium competitionPodium;

	/**
	 * Getter of the property <tt>competitionPodium</tt>
	 * 
	 * @return Returns the competitionPodium. uml.property
	 *         name="competitionPodium"
	 */
	public CompetitionPodium getCompetitionPodium() {
		return competitionPodium;
	}

	/**
	 * Setter of the property <tt>competitionPodium</tt>
	 * 
	 * @param competitionPodium
	 *            The competitionPodium to set. uml.property
	 *            name="competitionPodium"
	 */
	public void setCompetitionPodium(CompetitionPodium competitionPodium) {
		this.competitionPodium = competitionPodium;
	}

	
}
