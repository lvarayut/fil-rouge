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
						.println("la consultation des joueurs avec mdp gestionnaire non instanci� n'a pas lev� d'exception");
			} catch (AuthenticationException e) {
			}
			// Tests "number"
			if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 0)
				System.out
						.println("il existe des joueurs alors que le m�tier vient d'�tre construit");

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
			Calendar c = Calendar.getInstance();
			c.set(1989, 10, 10);
			aSoft.subscribe(new String("Prou"), new String("Bernard"),
					new String("nanard"),c, new String("ilesCaimans"));
			if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 5) {
				System.out.println("le nombre de joueurs est incorrect ");
			}

			// ***********************
			// * Tests "Unsubscribe" *
			// ***********************
			testUnsubscribe();

		} catch (Exception e) {
			System.out.println("\n Exception impr�vue : " + e);
			e.printStackTrace();
		}
	}

	private static void testChangeMngrPwd() throws BadParametersException,
			AuthenticationException {
		// Tests parameters : null
		try {
			aSoft.changeMngrPwd(null, null);
			System.out
					.println("changer le mdp gestionnaire avec des param�tres incorrects n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			aSoft.changeMngrPwd(new String("ilesCanaries"), null);
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("ilesCanaries"), new String("qsd"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.changeMngrPwd(null, new String("ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("d"), new String("ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas lev� d'exception  ");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.changeMngrPwd(new String("ddfqsdqsdfqsfdf$"), new String(
					"ilesCaimans"));
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide n'a pas lev� d'exception  ");
		} catch (BadParametersException e) {
		}
	}

	private static void testAuthenticateMngr() {
		try {
			aSoft.authenticateMngr(new String("ilesLofotens"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			aSoft.authenticateMngr(new String(" "));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			aSoft.authenticateMngr(null);
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}
	}

	private static void testUnsubscribe() throws AuthenticationException,
			ExistingSubscriberException {
		// Tests parameters : null
		try {
			aSoft.unsubscribe(null, new String("ilesCaimans"));
			System.out
					.println("retirer un joueur avec un pseudo non instanci� n'a pas lev� d'exception");
		} catch (ExistingSubscriberException e) {
		}
		try {
			aSoft.unsubscribe(new String("nanard"), null);
			System.out
					.println("retirer un joueur avec un mdp gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests parameters: incorrect manager password
		try {
			aSoft.unsubscribe(new String("nanard"), new String(" "));
			System.out
					.println(" retirer un joueur avec un mdp gestionnaire incorrect n'a pas lev� d'exception");
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
					.println("retirer un joueur existant a lev�e une exception");
		}

		number = aSoft.listSubscribers(new String("ilesCaimans")).size();

		// Unsubscribe an already unsubscriber subscriber
		try {
			aSoft.unsubscribe(new String("fanfan"), new String("ilesCaimans"));
			System.out
					.println("retirer un joueur d�j� retir� n'a lev�e d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Unsubscribe a non existing subscriber
		try {
			aSoft.unsubscribe(new String("tito"), new String("ilesCaimans"));
			System.out
					.println("retirer un joueur non enregistr� n'a lev�e d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Test number
		if (aSoft.listSubscribers(new String("ilesCaimans")).size() != 4) {
			System.out.println("le nombre de joueurs est incorrect");
		}
	}

	private static void testSubscribe() throws AuthenticationException,
			ExistingSubscriberException, BadParametersException {
		Calendar c = Calendar.getInstance();
		c.set(1989, 10, 10);
		// Tests entries : null
		try {
			aSoft.subscribe(null, new String("Albert"),
					new String("worldChamp"),c, new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un nom non instanci� n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), null,
					new String("worldChamp"), c,new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pr�nom non instanci� n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"), null,
					c,new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pseudo non instanci� n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					new String("worldChamp"), c,null);
			System.out
					.println("l'ajout d'un joueur avec un mdp gestionnaire non instanci� n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests entries : invalid format
		try {
			aSoft.subscribe(new String(" "), new String("Albert"), new String(
					"worldChamp"),c, new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un nom invalide n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}
		try {
			aSoft.subscribe(new String("Duran"), new String(" "), new String(
					"worldChamp"), c,new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pr�nom invalide n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}

		try {
			aSoft.subscribe(new String("Nobel"), new String("Alfred"),
					new String("tnt"), c,new String("ilesCaimans"));
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide n'a pas lev� d'exception");
		} catch (BadParametersException e) {
		}

		try {
			aSoft.subscribe(new String("Duran"), new String("Roberto"),
					new String("worldChamp"), c,new String("abef"));
			System.out
					.println("l'ajout d'un joueur avec un password gestionnaire incorrect n'a pas lev� d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests with valid parameters
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					new String("fanfan"), c,new String("ilesCaimans"));
		} catch (ExistingSubscriberException | BadParametersException e) {
			System.out
					.println("l'ajout d'un nouveau joueur a lev� une exception");
		}

		// The same subscriber
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					new String("fanfan"), c,new String("ilesCaimans"));
			System.out
					.println("1. l'ajout d'un joueur existant n'a pas lev� d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same firstname, username ; different lastname
		try {
			aSoft.subscribe(new String("Durano"), new String("Albert"),
					new String("fanfan"),c, new String("ilesCaimans"));
			System.out
					.println("2. l'ajout d'un joueur existant n'a pas lev� d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, username; different firstname
		try {
			aSoft.subscribe(new String("Duran"), new String("Alfred"),
					new String("fanfan"), c,new String("ilesCaimans"));
			System.out
					.println("3. l'ajout d'un joueur existant n'a pas lev� d'exception ");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, firstname; different username
		try {
			aSoft.subscribe(new String("Duran"), new String("Albert"),
					new String("fanfin"), c,new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("4. l'ajout d'un joueur pas inscrit a lev� une exception ");
		}

		// same firstname; different lastname, username
		try {
			aSoft.subscribe(new String("Durano"), new String("Albert"),
					new String("fanfin"), c,new String("ilesCaimans"));
			System.out
					.println("5. l'ajout d'un joueur inscrit n'a pas lev� d'exception ");
		} catch (ExistingSubscriberException e) {
		}

		// same lastname; different username and firstname
		try {
			aSoft.subscribe(new String("Duran"), new String("Morgan"),
					new String("fanfon"),c, new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("6. l'ajout d'un nouveau joueur a lev� une exception ");
		}

		// different lastname, firstname and username
		try {
			aSoft.subscribe(new String("Mato"), new String("Anna"), new String(
					"salto"), c,new String("ilesCaimans"));
		} catch (ExistingSubscriberException e) {
			System.out
					.println("7. l'ajout d'un nouveau joueur a lev� une exception ");
		}
	}
}