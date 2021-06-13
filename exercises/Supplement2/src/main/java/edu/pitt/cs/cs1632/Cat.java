package edu.pitt.cs.cs1632;
public class Cat {

	/**
	 * Indicates whether or not the cat is rented.
	 */

	boolean _rented = false;

	/**
	 * ID of the cat By default, -1
	 */
	int _id = -1;

	/**
	 * Name of the cat
	 */

	String _name;

	/**
	 * Whether bug injection is turned on for the Cat class. Turn off before actual
	 * usage. Only leave on during JUnit testing.
	 */

	public static boolean _bugInjectionOn = false;

	/**
	 * Constructor - creates a new Cat object Note there are no checks that this ID
	 * is not taken by another cat! This is probably something that we would fix in
	 * a production system.
	 * 
	 * @param int    id - the id number of this cat
	 * @param String name - the name of this Cat
	 */

	public Cat(int id, String name) {
		_rented = false;
		_id = id;
		_name = name;
	}

	/**
	 * Rent cat. Simply sets the _rented flag to true.
	 */

	public void rentCat() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for rentCat()");
		}
		_rented = true;
	}

	/**
	 * Return cat. Simply sets the _rented flag to false.
	 */

	public void returnCat() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for returnCat()");
		}
		_rented = false;
	}

	/**
	 * Accessor for _name variable. Returns the name of this cat.
	 * 
	 * @return String name of cat
	 */

	public String getName() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getName()");
		}
		return _name;
	}

	/**
	 * Accessor for _id variable. Returns the ID of this cat.
	 * 
	 * @return int ID of this cat
	 */

	public int getId() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getId()");
		}
		return _id;
	}

	/**
	 * Accessor for _rented variable. Returns if cat is rented.
	 * 
	 * @return boolean - true if rented, false otherwise
	 */

	public boolean getRented() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getRented()");
		}
		return _rented;
	}

	/**
	 * Returns string version of this cat, in form: "ID *id_num*. *name*" Example
	 * for cat of ID 1, name Jennyanydots: "ID 1. Jennyanydots"
	 * 
	 * @return String string version of cat
	 */

	public String toString() {
		if (_bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for toString()");
		}
		return "ID " + _id + ". " + _name;
	}

}
