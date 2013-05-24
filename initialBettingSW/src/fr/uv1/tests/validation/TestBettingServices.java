package fr.uv1.tests.validation;

import java.util.Calendar;

import fr.uv1.bettingServices.*;

/**
 * 
 * @author prou
 * 
 */
public class TestBettingServices {

	private static Betting aSoft;

	public static void main(String[] args) {

		try {
			aSoft = new BettingSoft(new String("ilesCaimans"));

			// ****************************
			// * Tests "List subscribers" *
			// ****************************
			// Tests "entries" : null
			try {
				aSoft.listSubscribers(null);
				System.out
						.println("la consultation des joueurs avec mdp gestionnaire non instancié n'a pas levé d'exception");
			} catch (AuthenticationException e) {
			}
			// Tests "number"
			if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 0)
				System.out
						.println("il existe des joueurs alors que le métier vient d'être construit");

			// ********************************
			// * Tests "authenticate manager" *
			// ********************************
			testAuthenticateMngr();

			// *************************************
			// * Tests "change manager's password" *
			// *************************************
			testChangeMngrPwd();

			// *********************
			// * Tests "subscribe" *
			// *********************
			testSubscribe();

			// ****************************
			// * Tests "List subscribers" *
			// ****************************

			// Tests number
			if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 4) {
				System.out.println("le nombre de joueurs est incorrect ");
			}
			aSoft.subscribe(new String("Prou"), new String("Bernard"),
					"1989-10-10",new String("nanard"),  new String("ilesCaimans"));
			if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 5) {
				System.out.println("le nombre de joueurs est incorrect ");
			}

			// ***********************
			// * Tests "Unsubscribe" *
			// ***********************
			testUnsubscribe();

		} catch (Exception e) {
			System.out.println("\n Exception imprévue : " + e);
			e.printStackTrace();
		}
	}

	private static void testChangeMngrPwd() throws BadParametersException,
			AuthenticationException {
		// Tests parameters : null
		try {
			aSoft.changeMngrPwd(null, null);
			System.out
					.println("changer le mdp gestionnaire avec des paramètres incorrects n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			aSoft.changeMngrPwd(new String("ilesCanaries"), null);
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("ilesCanaries"), new String("qsd"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.changeMngrPwd(null, new String("ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("d"), new String("ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas levé d'exception  ");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("ddfqsdqsdfqsfdf$"), new String(
					"ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas levé d'exception  ");
		} catch (BadParametersException e) {
		}
	}

	private static void testAuthenticateMngr() {
		try {
			aSoft.authenticateMngr(new String("ilesLofotens"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.authenticateMngr(new String(" "));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			aSoft.authenticateMngr(null);
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
	}

	private static void testUnsubscribe() throws AuthenticationException,
			ExistingSubscriberException {
		// Tests parameters : null
		try {
			aSoft.unsubscribe(null, new String("ilesCaimans"));
			System.out
					.println("retirer un joueur avec un pseudo non instancié n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		try {
			aSoft.unsubscribe(new String("nanard"), null);
			System.out
					.println("retirer un joueur avec un mdp gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests parameters: incorrect manager password
		try {
			aSoft.unsubscribe(new String("nanard"), new String(" "));
			System.out
					.println(" retirer un joueur avec un mdp gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Test number
		int number = aSoft.listSubscribers(new String("ilesCaimans")).size();
		if (number != 5) {
			System.out.println("le nombre de joueurs est incorrect");
		}

		// Unsubscribe an existing subscriber
		try {
			aSoft.unsubscribe(new String("fanfan"), new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("retirer un joueur existant a levée une exception");
		}

		number = aSoft.listSubscribers(new String("ilesCaimans")).size();

		// Unsubscribe an already unsubscriber subscriber
		try {
			aSoft.unsubscribe(new String("fanfan"), new String("ilesCaimans"));
			System.out
					.println("retirer un joueur déjà retiré n'a levée d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Unsubscribe a non existing subscriber
		try {
			aSoft.unsubscribe(new String("tito"), new String("ilesCaimans"));
			System.out
					.println("retirer un joueur non enregistré n'a levée d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Test number
		if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 4) {
			System.out.println("le nombre de joueurs est incorrect");
		}
	}

	private static void testSubscribe() throws AuthenticationException,
			ExistingSubscriberException, BadParametersException {
		// Tests entries : null
		try {
			aSoft.subscribe(null, new String("Albert"),
					"1989-10-10",new String("worldChamp"),  new String(
							"ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un nom non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), null,
					"1989-10-10",new String("worldChamp"),  new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un prénom non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),"1989-10-10", null, 
					new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pseudo non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					"1989-10-10",new String("worldChamp"),  null);
			System.out
					.println("l'ajout d'un joueur avec un mdp gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests entries : invalid format
		try {
			aSoft.subscribe(new String(" "), new String("Albert"), "1989-10-10",new String(
					"worldChamp"),  new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un nom invalide n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String(" "),"1989-10-10", new String(
					"worldChamp"),  new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un prénom invalide n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			aSoft.subscribe(new String("Nobel"), new String("Alfred"),
					"1989-10-10",new String("tnt"),  new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			aSoft.subscribe(new String("Duran"), new String("Roberto"),
					"1989-10-10",new String("worldChamp"),  new String("abef"));
			System.out
					.println("l'ajout d'un joueur avec un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests with valid parameters
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					"1989-10-10",new String("fanfan"),  new String("ilesCaimans"));
		} catch (ExistingSubscriberException | BadParametersException e) {
			System.out
					.println("l'ajout d'un nouveau joueur a levé une exception");
		}

		// The same subscriber
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					"1989-10-10",	new String("fanfan"),  new String("ilesCaimans"));
			System.out
					.println("1. l'ajout d'un joueur existant n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same firstname, username ; different lastname
		try {
			aSoft.subscribe(new String("Durano"), new String("Albert"),
					"1989-10-10",new String("fanfan"),  new String("ilesCaimans"));
			System.out
					.println("2. l'ajout d'un joueur existant n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, username; different firstname
		try {
			aSoft.subscribe(new String("Duran"), new String("Alfred"),
					"1989-10-10",new String("fanfan"),  new String("ilesCaimans"));
			System.out
					.println("3. l'ajout d'un joueur existant n'a pas levé d'exception ");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, firstname; different username
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					 "1989-10-10",new String("fanfin"), new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("4. l'ajout d'un joueur pas inscrit a levé une exception ");
		}

		// same firstname; different lastname, username
		try {
			aSoft.subscribe(new String("Durano"), new String("Albert"),
					"1989-10-10",	new String("fanfin"),  new String("ilesCaimans"));
			System.out
					.println("5. l'ajout d'un joueur inscrit n'a pas levé d'exception ");
		} catch (ExistingSubscriberException e) {
		}

		// same lastname; different username and firstname
		try {
			aSoft.subscribe(new String("Duran"), new String("Morgan"),
					"1989-10-10",new String("fanfon"),  new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("6. l'ajout d'un nouveau joueur a levé une exception ");
		}

		// different lastname, firstname and username
		try {
			aSoft.subscribe(new String("Mato"), new String("Anna"), "1989-10-10",new String(
					"salto"),  new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("7. l'ajout d'un nouveau joueur a levé une exception ");
		}
	}
}