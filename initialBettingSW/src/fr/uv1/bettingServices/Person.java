package fr.uv1.bettingServices;

import java.util.Calendar;


public abstract class Person {
	
	
	

	private String firstname;
	private String lastname;
	private Calendar birthdate;
	

	public Person(String firstname,String lastname,Calendar birthDate) {
		// TODO Auto-generated constructor stub
		try {
			this.setFirstname(firstname);
			this.setLastname(lastname);
			this.setBirthdate(birthDate);
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * @param lastname Person's last name
	 * @throws BadParametersException The lastname object cannot be null
	 */
	public void setLastname(String lastname) throws BadParametersException {
		if (lastname == null)
			throw new BadParametersException("The lastname object cannot be null");
		checkStringLastName(lastname);
		this.lastname = lastname;
	}

	/**
	 * @param firstname Person's first name
	 * @throws BadParametersException The firstname cannot be null
	 */
	public void setFirstname(String firstname) throws BadParametersException {
		if (firstname == null)
			throw new BadParametersException("The firstname cannot be null");
		checkStringFirstName(firstname);
		this.firstname = firstname;
	}

	
	
	
	
	/**
	 * check the validity of a string for a subscriber lastname, letters, dashes
	 * and spaces are allowed. First character should be a letter. lastname
	 * length should at least be 1 character
	 * 
	 * @param a_lastname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	protected static void checkStringLastName(String a_lastname)
			throws BadParametersException {
		if (a_lastname == null)
			throw new BadParametersException("name not instantiated");
		if (a_lastname.length() < 1)
			throw new BadParametersException(
					"name length less than 1 character");
		// First character a letter
		char c = a_lastname.charAt(0);
		if (!Character.isLetter(c))
			throw new BadParametersException(
					"first character is not a letter");
		for (int i = 1; i < a_lastname.length(); i++) {
			c = a_lastname.charAt(i);
			if (!Character.isLetter(c) && (c != '-') && (c != ' '))
				throw new BadParametersException(
						"the name "
								+ a_lastname
								+ " contains other characters than letters, dashes and spaces");
		}
	}

	/**
	 * check the validity of a string for a subscriber firstname, letters,
	 * dashes and spaces are allowed. First character should be a letter.
	 * firstname length should at least be 1 character
	 * Bet
	 * @param a_firstname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringFirstName(String a_firstname)
			throws BadParametersException {
		// Same rules as for the last name
		checkStringLastName(a_firstname);
		
	}

	
	

	/**
	 * uml.property  name="bettingSoft"
	 * uml.associationEnd  multiplicity="(1 1)" inverse="person:fr.uv1.bettingServices.BettingSoft"
	 */
	private BettingSoft bettingSoft;


	/**
	 * Getter of the property <tt>bettingSoft</tt>
	 * @return  Returns the bettingSoft.
	 * uml.property  name="bettingSoft"
	 */
	public BettingSoft getBettingSoft() {
		return bettingSoft;
	}

	/**
	 * Setter of the property <tt>bettingSoft</tt>
	 * @param bettingSoft  The bettingSoft to set.
	 * uml.property  name="bettingSoft"
	 * @throws BadParametersException The bettingSoft object cannot be null
	 */
	public void setBettingSoft(BettingSoft bettingSoft) throws BadParametersException {
		if (bettingSoft == null)
			throw new BadParametersException("The bettingSoft object cannot be null");
		this.bettingSoft = bettingSoft;
	}

	public Calendar getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate Calendar object of birthdate
	 * @throws BadParametersException "The birthdate object cannot be null"
	 */
	public void setBirthdate(Calendar birthdate) throws BadParametersException {
		if (birthdate == null)
			throw new BadParametersException("The birthdate object cannot be null");
		this.birthdate = birthdate;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}


}
