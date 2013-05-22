package fr.uv1.tests.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.bettingServices.AuthenticationException;
import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.BettingSoft;
import fr.uv1.bettingServices.CompetitionException;
import fr.uv1.bettingServices.PCompetitor;
import fr.uv1.bettingServices.ExistingCompetitionException;
import fr.uv1.bettingServices.ExistingSubscriberException;
import fr.uv1.bettingServices.Subscriber;

public class TestBettingSoft {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BettingSoft bs = null;
		try {
			bs = new BettingSoft("rokhayagaye");
			
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			System.out.println("Bad Parameters Exception");
		}
		
		/*
		 * Test addCompetitor
		 */
		System.out.println("----------------Add Competitor ----------------");
		Calendar s1 = Calendar.getInstance(); //Current time
		s1.set(2013, Calendar.AUGUST, 20);
		PCompetitor cTor = bs.createCompetitor("vlerd","Varayut", "Lerd", s1);
		
		/*
		 * Test addCompetition
		 */
		System.out.println("----------------Add Competition ---------------");
		Collection comName = new ArrayList<PCompetitor>();
		comName.add(cTor);
		try {
			bs.addCompetition("Polo", s1, comName, "rokhayagaye");
		} catch (AuthenticationException | ExistingCompetitionException
				| CompetitionException | BadParametersException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*
		 * Test listCompetition function 
		 */
		System.out.println("----------------List competitions ---------------");
		bs.listCompetitions();
		
		
		/*
		 * Test addSubscriber (age > 18) 
		 */
		System.out.println("----------------Add Subscriber ---------------");
		Calendar c1 = Calendar.getInstance(); //Current time
		c1.set(1989,Calendar.OCTOBER,10);
		try {
			bs.subscribe("GAYE", "Rokhaya", "rgaye", c1, "rokhayagaye");
		} catch (AuthenticationException | ExistingSubscriberException
				| BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Test credit  
		 */
		System.out.println("----------------Add tokens ---------------");
		try {
			bs.creditCompetitor("rgaye", 20,  "rokhayagaye");
		} catch (AuthenticationException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Test debit
		 */
		System.out.println("----------------Remove tokens ---------------");
		try {
			bs.debitSubscriber("rgaye", -10,  "rokhayagaye");
		} catch (AuthenticationException | BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Test betOnPodium  
		 */
		System.out.println("----------------Bet on podium ---------------");
		PCompetitor cTor1 = bs.createCompetitor("A_First", "A_Last", "A_Username", Calendar.getInstance());
		PCompetitor cTor2 = bs.createCompetitor("B_First", "B_Last", "B_Username", Calendar.getInstance());
		PCompetitor cTor3 = bs.createCompetitor("C_First", "C_Last", "C_Username", Calendar.getInstance());
		try {
			Subscriber s = new Subscriber("subLast", "subFirst", "sub", s1);
			s.betOnPodium(10, "Polo", cTor1, cTor2, cTor3, "rgaye",  "rokhayagaye");
		} catch (BadParametersException | AuthenticationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		/*
//		 * Test calculatePodiumWinner
//		 */
//		System.out.println("----------------Calculate Podium Winner ---------------");
//		try {
//			bs.calculatePodiumWinner("Polo", cTor1, cTor2, cTor3, "rokhayagaye");
//		} catch (AuthenticationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/*
		 * Test settlePodium
		 */
		System.out.println("----------------Settle Podium ---------------");
		try {
			bs.settlePodium("Polo", cTor1, cTor2, cTor3, "rokhayagaye");
		} catch (AuthenticationException | SQLException
				| ExistingCompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
