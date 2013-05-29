package fr.uv1.bettingServices;

import java.sql.SQLException;

import fr.uv1.DAO.CompetitionDAO;
import fr.uv1.DAO.PodiumDAO;
import fr.uv1.DAO.WinnerDAO;

public class BetWinner extends Bet {

	public BetWinner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 */
	public void betOnWinner(long number_tokens, String a_competition,
			PCompetitor a_winner, String a_username, String a_pwdsubs)
			throws AuthenticationException, CompetitionException,
			ExistingCompetitionException, SubscriberException,
			BadParametersException {
	}

	/**
	 * Settle bets on winner
	 * 
	 * @param competition
	 *            Competition's name
	 * @param winner
	 *            Winner
	 * @param managerPwd
	 *            Manager's password
	 * @throws AuthenticationException
	 *             The manager's password is invalid
	 * @throws ExistingCompetitionException
	 *             The competition is not existed
	 * @throws SQLException
	 *             SQL problem
	 * @throws BadParametersException 
	 */
	public void settleWinner(String a_competition, PCompetitor a_winner,
			String a_managerPwd) throws AuthenticationException,
			ExistingCompetitionException, CompetitionException, BadParametersException, SQLException {
		// Authenticate manager
		BettingSoft bs = new BettingSoft(a_managerPwd);
		bs.authenticateMngr(a_managerPwd);
		CompetitionDAO cd = new CompetitionDAO();
		// Exist Competition
		if (!cd.isExistCompetition(a_competition))
			throw new ExistingCompetitionException(
					"The competition doesn't exist");
		WinnerDAO wd = new WinnerDAO();
		wd.settleWinnerToSubscriber(a_competition, a_winner);
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
