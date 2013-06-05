package fr.uv1.tests.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.bettingServices.AuthenticationException;
import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.BetPodium;
import fr.uv1.bettingServices.BetWinner;
import fr.uv1.bettingServices.BettingSoft;
import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.CompetitionException;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.ExistingCompetitorException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.ExistingCompetitionException;
import fr.uv1.bettingServices.ExistingSubscriberException;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.SubscriberException;

public class TestBettingSoft {

	private static BettingSoft bs = null;
	private static Subscriber sub1 = null;
	private static Subscriber sub2 = null;
	private static ArrayList<Competitor> comTors = null;
	private static Competition comTion = null;

	/**
	 * @param args
	 * @throws AuthenticationException
	 * @throws BadParametersException
	 * @throws CompetitionException
	 * @throws ExistingCompetitorException
	 * @throws SQLException
	 * @throws ExistingSubscriberException
	 * @throws SubscriberException
	 * @throws ExistingCompetitionException
	 */
	public static void main(String[] args) throws BadParametersException,
			AuthenticationException, CompetitionException,
			ExistingCompetitorException, SQLException, SubscriberException,
			ExistingSubscriberException, ExistingCompetitionException {
		// Initialize necessary objects for testing
		bs = new BettingSoft("managerpwd");
		comTors = new ArrayList<Competitor>();
		Calendar s1 = Calendar.getInstance(); // Current time
		s1.set(2013, 8, 20);
		comTion = new Competition("Polo", Calendar.getInstance(), s1);
		Calendar c1 = Calendar.getInstance();
		c1.set(1980, 10, 10);
		Calendar c2 = Calendar.getInstance();
		c2.set(1990, 1, 1);
		sub1 = new Subscriber("SFNOne", "SNOne", "unOne", c1);
		sub2 = new Subscriber("SFNTwo", "SNTwo", "unTwo", c2);

		 // Add two subscriber into database
		 testAddSubscriber();
		
		 // Credit each subscriber for 50 tokens
		 testCreditSubscriber();
		
		 // Debit each subscriber for 10 tokens
		 testDebitSubscriber();
		
		// Add four competitors into database
		testCreateCompetitor();

		// Add a competition, "Polo"
		testAddCompetition();

		// Participate competitors to a competition
		testAddCompetitior();
		//
		// // List the competition, the Polo should be shown
		testListCompetition();

		// Calculate winner, in our case, we fixed winner,second, and third
		// of the four competitors
		testCalculatePodiumWinner();

		// Calculate winner, in our case, we fixed winner of the four
		// competitors
		testCalculateWinnerWinner();

		// The first subscriber bet on com1,com2, and com3
		// The second subscriber bet on com2,com3, and com4
		testBetOnPodium();

		// The first subscriber bet on com1
		// The second subscriber bet on com2
		testBetOnWinner();

		// Settle money to all subscribers who bet on podium
		testSettlePodium();
		// Settle money to all subscribers who bet on winner
		testSettleWinner();

	}

	private static void testAddCompetitior() throws AuthenticationException,
			SQLException, ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException {
		// TODO Auto-generated method stub
		bs.addCompetitor(comTion, comTors, bs.getManagerPassword());

	}

	public static void testCreateCompetitor() throws BadParametersException {
		/*
		 * Test addCompetitor
		 */
		System.out.println("----------------Add Competitor ----------------");
		Calendar s1 = Calendar.getInstance(); // Current time
		s1.set(2013, Calendar.OCTOBER, 20);
		try {
			comTors.add(bs.createCompetitor("CFOne", "CLOne", s1,
					bs.getManagerPassword()));
			comTors.add(bs.createCompetitor("CFTwo", "CLTwo", s1,
					bs.getManagerPassword()));
			comTors.add(bs.createCompetitor("CFThree", "CLThree", s1,
					bs.getManagerPassword()));
			comTors.add(bs.createCompetitor("CFFour", "CLFour", s1,
					bs.getManagerPassword()));

		} catch (AuthenticationException | ExistingCompetitorException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public static void testAddCompetition() throws BadParametersException,
			AuthenticationException, SQLException {
		/*
		 * Test addCompetition
		 */
		System.out.println("----------------Add Competition ---------------");

		try {
			bs.addCompetition(comTion.getName(), comTion.getEndDate(), comTors,
					bs.getManagerPassword());
		} catch (AuthenticationException | ExistingCompetitionException
				| BadParametersException | CompetitionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void testListCompetition() throws BadParametersException {
		/*
		 * Test listCompetition function
		 */
		System.out.println("----------------List competitions ---------------");
		bs.listCompetitions();

	}

	public static void testAddSubscriber() throws BadParametersException {
		/*
		 * Test addSubscriber (age > 18)
		 */
		System.out.println("----------------Add Subscriber ---------------");
		try {
			sub1.setPassword(bs.subscribe(sub1.getLastname(),
					sub1.getFirstname(), sub1.convertBirthdayToString(),
					sub1.getUsername(), bs.getManagerPassword()));
			sub2.setPassword(bs.subscribe(sub2.getLastname(),
					sub2.getFirstname(), sub2.convertBirthdayToString(),
					sub2.getUsername(), bs.getManagerPassword()));
		} catch (AuthenticationException | ExistingSubscriberException
				| BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testCreditSubscriber() throws BadParametersException {
		/*
		 * Test credit
		 */
		System.out.println("----------------Add tokens ---------------");
		try {
			bs.creditSubscriber(sub1.getUsername(), 50, bs.getManagerPassword());
			bs.creditSubscriber(sub2.getUsername(), 50, bs.getManagerPassword());
		} catch (AuthenticationException | BadParametersException
				| SQLException | ExistingSubscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDebitSubscriber() throws BadParametersException {
		/*
		 * Test debit
		 */
		System.out.println("----------------Remove tokens ---------------");
		try {
			bs.debitSubscriber(sub1.getUsername(), 10, bs.getManagerPassword());
			bs.debitSubscriber(sub2.getUsername(), 10, bs.getManagerPassword());
		} catch (AuthenticationException | BadParametersException
				| SubscriberException | SQLException
				| ExistingSubscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testBetOnPodium() throws BadParametersException,
			AuthenticationException, SQLException, CompetitionException,
			SubscriberException, ExistingSubscriberException {
		/*
		 * Test betOnPodium
		 */

		System.out.println("----------------Bet on podium ---------------");
		BetPodium bp = new BetPodium();
		bp.betOnPodium(10, "Polo", comTors.get(0), comTors.get(1),
				comTors.get(2), sub1.getUsername(), sub1.getPassword());
		bp.betOnPodium(10, "Polo", comTors.get(1), comTors.get(2),
				comTors.get(3), sub2.getUsername(), sub2.getPassword());
		// try {
		// Subscriber s = new Subscriber("subLast", "subFirst", "pass", s1);
		// SubscriberDAO sd = new SubscriberDAO();
		// // sd.addSubscriber(s);

		// // s.getPassword());
		// } catch (BadParametersException | AuthenticationException
		// | SQLException | ExistingSubscriberException
		// | CompetitionException | SubscriberException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

	}

	public static void testCalculatePodiumWinner()
			throws BadParametersException, AuthenticationException,
			ExistingCompetitorException {
		/*
		 * Test CalculatePodiumWinner
		 */
		System.out
				.println("----------------Calculate Podium Winner---------------");
		bs.calculatePodiumWinner("Polo", (PCompetitor) comTors.get(0),
				(PCompetitor) comTors.get(1), (PCompetitor) comTors.get(2),
				bs.getManagerPassword());
	}

	public static void testSettlePodium() throws BadParametersException,
			AuthenticationException, CompetitionException,
			ExistingCompetitorException, SQLException,
			ExistingCompetitionException {
		/*
		 * Test settlePodium
		 */
		System.out.println("----------------Settle Podium ---------------");
		BetPodium bp = new BetPodium();
		// BettingSoft bs = new BettingSoft("rokhayagaye");
		// PCompetitor cTor1 = (PCompetitor) bs.createCompetitor("AFirst",
		// "ALast", Calendar.getInstance(), "rokhayagaye");
		// PCompetitor cTor2 = (PCompetitor) bs.createCompetitor("BFirst",
		// "BLast", Calendar.getInstance(), "rokhayagaye");
		// PCompetitor cTor3 = (PCompetitor) bs.createCompetitor("CFirst",
		// "CLast", Calendar.getInstance(), "rokhayagaye");

		bp.settlePodium("Polo", comTors.get(0), comTors.get(1), comTors.get(2),
				bs.getManagerPassword());

	}

	public static void testCalculateWinnerWinner()
			throws BadParametersException, AuthenticationException,
			ExistingCompetitorException {
		/*
		 * Test CalculateWinnerWinner
		 */
		System.out
				.println("----------------Calculate Winner Winner---------------");
		bs.calculateWinnerWinner("Polo", (PCompetitor) comTors.get(0),
				bs.getManagerPassword());
	}

	public static void testSettleWinner() throws BadParametersException,
			AuthenticationException, CompetitionException,
			ExistingCompetitorException, ExistingCompetitionException,
			SQLException {
		/*
		 * Test settleWinner
		 */
		System.out.println("----------------Settle Winner ---------------");
		BetWinner bw = new BetWinner();
		bw.settleWinner("Polo", comTors.get(0), bs.getManagerPassword());
		// BettingSoft bs = new BettingSoft("rokhayagaye");
		// PCompetitor cTor1 = (PCompetitor) bs.createCompetitor("AFirst",
		// "ALast", Calendar.getInstance(), "rokhayagaye");
		// try {
		// bw.settleWinner("Polo", cTor1, "rokhayagaye");
		// } catch (AuthenticationException | SQLException
		// | ExistingCompetitionException | BadParametersException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void testBetOnWinner() throws BadParametersException,
			AuthenticationException, CompetitionException,
			ExistingCompetitionException, SubscriberException, SQLException,
			ExistingSubscriberException {
		/*
		 * Test betOnWinner
		 */

		System.out.println("----------------Bet on winner ---------------");
		BetWinner bw = new BetWinner();
		bw.betOnWinner(10, "Polo", comTors.get(0), sub1.getUsername(),
				sub1.getPassword());
		bw.betOnWinner(10, "Polo", comTors.get(1), sub2.getUsername(),
				sub2.getPassword());
		// BettingSoft bs = new BettingSoft("rokhayagaye");
		// Calendar s1 = Calendar.getInstance(); // Current time
		// s1.set(2013, Calendar.AUGUST, 20);
		// PCompetitor cTor = null;
		// try {
		// cTor = (PCompetitor) bs.createCompetitor("AFirst", "ALast",
		// Calendar.getInstance(), "rokhayagaye");
		//
		// } catch (AuthenticationException | ExistingCompetitorException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
		//
		// try {
		// Subscriber s = new Subscriber("subLast", "subFirst", "pass", s1);
		// SubscriberDAO sd = new SubscriberDAO();
		// sd.addSubscriber(s);
		// BetWinner bp = new BetWinner();
		// bp.betOnWinner(10, "Polo", cTor, "rgaye", s.getPassword());
		// } catch (BadParametersException | AuthenticationException
		// | SQLException | ExistingSubscriberException
		// | CompetitionException | ExistingCompetitionException
		// | SubscriberException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

	}
}
