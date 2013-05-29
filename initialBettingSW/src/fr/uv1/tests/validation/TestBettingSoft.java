package fr.uv1.tests.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.DAO.SubscriberDAO;
import fr.uv1.bettingServices.AuthenticationException;
import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.BetPodium;
import fr.uv1.bettingServices.BettingSoft;
import fr.uv1.bettingServices.CompetitionException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.ExistingCompetitionException;
import fr.uv1.bettingServices.ExistingSubscriberException;
import fr.uv1.bettingServices.Subscriber;

public class TestBettingSoft {

	/**
	 * @param args
	 * @throws AuthenticationException 
	 * @throws BadParametersException 
	 */
	public static void main(String[] args) throws BadParametersException, AuthenticationException {
		// TODO Auto-generated method stub

		BettingSoft bs = null;
		try {
			bs = new BettingSoft("rokhayagaye");

		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			System.out.println("Bad Parameters Exception");
		}
		
		testAddCompetition();
		testAddCompetitor();
		testAddSubscriber();
		testBetOnPodium();
		testCreditSubscriber();
		testDebitSubscriber();
		testListCompetition();
		testSettlePodium();
		// /*
		// * Test calculatePodiumWinner
		// */
		// System.out.println("----------------Calculate Podium Winner ---------------");
		// try {
		// bs.calculatePodiumWinner("Polo", cTor1, cTor2, cTor3, "rokhayagaye");
		// } catch (AuthenticationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static void testAddCompetitor() throws BadParametersException {
		/*
		 * Test addCompetitor
		 */
		BettingSoft bs = new BettingSoft("rokhayagaye");
		System.out.println("----------------Add Competitor ----------------");
		Calendar s1 = Calendar.getInstance(); // Current time
		s1.set(2013, Calendar.AUGUST, 20);
		PCompetitor cTor = null;
		try {
			cTor = bs.createCompetitor("Varayut", "Lerd", s1, "rokhayagaye");
		} catch (AuthenticationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public static void testAddCompetition() throws BadParametersException,
			AuthenticationException {
		/*
		 * Test addCompetition
		 */
		System.out.println("----------------Add Competition ---------------");
		BettingSoft bs = new BettingSoft("rokhayagaye");
		Collection<PCompetitor> comName = new ArrayList<PCompetitor>();
		Calendar s1 = Calendar.getInstance(); // Current time
		PCompetitor cTor = bs.createCompetitor("Varayut", "Lerd", s1,
				"rokhayagaye");
		comName.add(cTor);
		try {
			bs.addCompetition("Polo", s1, comName, "rokhayagaye");
		} catch (AuthenticationException | ExistingCompetitionException
				| BadParametersException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void testListCompetition() throws BadParametersException {

		/*
		 * Test listCompetition function
		 */
		System.out.println("----------------List competitions ---------------");
		BettingSoft bs = new BettingSoft("rokhayagaye");
		bs.listCompetitions();

	}

	public static void testAddSubscriber() throws BadParametersException {
		/*
		 * Test addSubscriber (age > 18)
		 */
		System.out.println("----------------Add Subscriber ---------------");
		BettingSoft bs = new BettingSoft("rokhayagaye");
		try {
			bs.subscribe("GAYE", "Rokhaya", "1989-10-10", "rgaye",
					"rokhayagaye");
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
		BettingSoft bs = new BettingSoft("rokhayagaye");
		try {
			bs.creditSubscriber("rgaye", 20, "rokhayagaye");
		} catch (AuthenticationException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDebitSubscriber() throws BadParametersException {
		/*
		 * Test debit
		 */
		System.out.println("----------------Remove tokens ---------------");
		BettingSoft bs = new BettingSoft("rokhayagaye");
		try {
			bs.debitSubscriber("rgaye", -10, "rokhayagaye");
		} catch (AuthenticationException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testBetOnPodium() throws BadParametersException {
		/*
		 * Test betOnPodium
		 */

		System.out.println("----------------Bet on podium ---------------");
		BettingSoft bs = new BettingSoft("rokhayagaye");
		Calendar s1 = Calendar.getInstance(); // Current time
		s1.set(2013, Calendar.AUGUST, 20);
		PCompetitor cTor1 = null;
		PCompetitor cTor2 = null;
		PCompetitor cTor3 = null;
		try {
			cTor1 = bs.createCompetitor("AFirst", "ALast",
					Calendar.getInstance(), "rokhayagaye");
			cTor2 = bs.createCompetitor("BFirst", "BLast",
					Calendar.getInstance(), "rokhayagaye");
			cTor3 = bs.createCompetitor("CFirst", "CLast",
					Calendar.getInstance(), "rokhayagaye");
		} catch (AuthenticationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			Subscriber s = new Subscriber("subLast", "subFirst", "pass", s1);
			SubscriberDAO sd = new SubscriberDAO();
			sd.addSubscriber(s);
			BetPodium bp = new BetPodium();
			bp.betOnPodium(10, "Polo", cTor1, cTor2, cTor3, "rgaye",
					s.getPassword());
		} catch (BadParametersException | AuthenticationException
				| SQLException | ExistingSubscriberException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void testSettlePodium() throws BadParametersException, AuthenticationException {
		/*
		 * Test settlePodium
		 */
		System.out.println("----------------Settle Podium ---------------");
		BetPodium bp = new BetPodium();
		BettingSoft bs = new BettingSoft("rokhayagaye");
		PCompetitor cTor1 = bs.createCompetitor("AFirst", "ALast",
				Calendar.getInstance(), "rokhayagaye");;
		PCompetitor cTor2 = bs.createCompetitor("BFirst", "BLast",
				Calendar.getInstance(), "rokhayagaye");
		PCompetitor cTor3 = bs.createCompetitor("CFirst", "CLast",
				Calendar.getInstance(), "rokhayagaye");
		try {
			bp.settlePodium("Polo", cTor1, cTor2, cTor3, "rokhayagaye");
		} catch (AuthenticationException | SQLException
				| ExistingCompetitionException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
