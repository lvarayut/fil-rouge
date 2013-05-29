package fr.uv1.bettingServices;

public class ExistingCompetitorException extends Exception{

	public ExistingCompetitorException() {
		super();
	}
	public ExistingCompetitorException(String reason) {
		super(reason);
	}
}
