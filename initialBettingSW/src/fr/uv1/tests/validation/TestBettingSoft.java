package fr.uv1.tests.validation;

import java.sql.ResultSet;
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
		bs.createCompetitor("vlerd","Varayut", "Lerd", s1);
		
		/*
		 * Test addCompetition
		 */
		System.out.println("----------------Add Competition ---------------");
		Collection comName = new ArrayList<PCompetitor>();
		PCompetitor cTor = new PCompetitor("Varayut", "Lerd","vlerd",s1);
		comName.add(cTor);
		try {
			bs.addCompetition("Polo", s1, comName, "rokhayagaye");
		} catch (AuthenticationException | ExistingCompetitionException
				| CompetitionException | BadParametersException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		/*
//		 * Test listCompetition function 
//		 */
//		System.out.println("----------------List competitions ---------------");
//		bs.listCompetitions();
//		
//		
//		/*
//		 * Test addSubscriber (age > 18) 
//		 */
//		System.out.println("----------------Add Subscriber ---------------");
//		Calendar c1 = Calendar.getInstance(); //Current time
//		c1.set(1989,Calendar.OCTOBER,10);
//		try {
//			bs.subscribe("GAYE", "Rokhaya", "rgaye", c1, "rokhayagaye");
//		} catch (AuthenticationException | ExistingSubscriberException
//				| BadParametersException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		


	}
}
