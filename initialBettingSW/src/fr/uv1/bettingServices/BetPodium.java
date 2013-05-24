package fr.uv1.bettingServices;

import java.util.Collection;


public class BetPodium extends Bet {

	public BetPodium() {
		// TODO Auto-generated constructor stub
	}
	/**
	 */
	public void settlePodium(String a_competition, PCompetitor a_winner, PCompetitor a_second, PCompetitor a_third, String a_managerPwd)
			throws AuthenticationException, ExistingCompetitionException, CompetitionException {
	}


		
		/**
		 */
		public void betOnPodium(long number_tokens, String a_competition, PCompetitor a_winner, PCompetitor a_second, PCompetitor a_third, String a_username, String a_pwdSubs)	
				throws AuthenticationException, CompetitionException, ExistingCompetitionException, SubscriberException, BadParametersException {
		}



		/**
		 * uml.property  name="competitor"
		 * uml.associationEnd  multiplicity="(3 3)" inverse="betPodium:fr.uv1.bettingServices.Competitor"
		 */
		private Collection competitor;



		/**
		 * Getter of the property <tt>competitor</tt>
		 * @return  Returns the competitor.
		 * uml.property  name="competitor"
		 */
		public Collection getCompetitor() {
			return competitor;
		}
		/**
		 * Setter of the property <tt>competitor</tt>
		 * @param competitor  The competitor to set.
		 * uml.property  name="competitor"
		 */
		public void setCompetitor(Collection competitor) {
			this.competitor = competitor;
		}



		/**
		 * uml.property  name="competitionPodium"
		 * uml.associationEnd  multiplicity="(1 1)" inverse="betPodium:fr.uv1.bettingServices.CompetitionPodium"
		 */
		private CompetitionPodium competitionPodium;



		/**
		 * Getter of the property <tt>competitionPodium</tt>
		 * @return  Returns the competitionPodium.
		 * uml.property  name="competitionPodium"
		 */
		public CompetitionPodium getCompetitionPodium() {
			return competitionPodium;
		}
		/**
		 * Setter of the property <tt>competitionPodium</tt>
		 * @param competitionPodium  The competitionPodium to set.
		 * uml.property  name="competitionPodium"
		 */
		public void setCompetitionPodium(CompetitionPodium competitionPodium) {
			this.competitionPodium = competitionPodium;
		}


}
