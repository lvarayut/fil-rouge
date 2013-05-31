package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.*;

import fr.uv1.bettingServices.*;


public class SubscriberTest {

	private Subscriber subs;

	@Before
	public void setUp() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(1989, 10, 10);
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("worldChamp"),c);
	}

	/*
	 * Subscribers should be created with valid strings
	 */
	@Test
	public void testSubscriber() {
		assertTrue(subs.getFirstname().equals("Miguel"));
		assertTrue(subs.getLastname().equals("Duran"));
		assertTrue(subs.getUsername().equals("worldChamp"));
		assertTrue(subs.getPassword() != null);
	}

	@Test(expected=BadParametersException.class)
	public void testInvalidSubscriber() throws BadParametersException {
		// not instantiated parameters
//		new Subscriber(null, null, null);
//		new Subscriber(null, null, new String("worldChamp"));
//		new Subscriber(new String("Duran"), null, new String("worldChamp"));
//		new Subscriber(null, new String("Miguel"), new String("worldChamp"));
//		new Subscriber(null, new String("Miguel"), null);
//		new Subscriber(new String("Duran"), null, null);
//		new Subscriber(new String("Duran"), new String("Miguel"), null);
		
		// invalid parameters
//		new Subscriber(new String(""), new String(""), new String(""));
//		new Subscriber(new String(""), new String(""), new String("worldChamp"));
//		new Subscriber(new String(""), new String("Miguel"), new String("worldChamp"));
//		new Subscriber(new String("Duran"), new String(""), new String("worldChamp"));
//		new Subscriber(new String("Duran"), new String(""), new String(""));
//		new Subscriber(new String("Duran"), new String(""), new String("worldChamp"));
		Calendar c = Calendar.getInstance();
		c.set(1989, 10, 10);
		new Subscriber(new String("Duran"), new String("Miguel"), new String(""),c);
	}
	
	@Test
	public void testHasUsername() {
		assertTrue(subs.getUsername().equals("worldChamp"));

		assertFalse(subs.getUsername().equals("wddfkjddfk"));
	}

	@Test
	public void testEqualsObject() throws BadParametersException {
		// Same subscriber = same username
		Calendar c = Calendar.getInstance();
		c.set(1989, 10, 10);
		Subscriber s = new Subscriber(new String("Duran"),
				new String("Miguel"), new String("worldChamp"),c);
		assertTrue(subs.equals(s));

		s = new Subscriber(new String("Durano"), new String("Miguel"),
				new String("worldChamp"),c);
		assertTrue(subs.equals(s));

		s = new Subscriber(new String("Duran"), new String("Miguelo"),
				new String("worldChamp"),c);
		assertTrue(subs.equals(s));

		s = new Subscriber(new String("Durano"), new String("Miguelo"),
				new String("worldChamp"),c);
		assertTrue(subs.equals(s));

		// Different subscriber = different username
		s = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("worldC"),c);
		assertFalse(subs.equals(s));
	}
}