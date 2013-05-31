package fr.uv1.tests.validation;

import fr.uv1.bettingServices.BadParametersException;
import fr.uv1.bettingServices.Betting;
import fr.uv1.bettingServices.BettingSoft;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	FirstIncrementValidationTests t = new FirstIncrementValidationTests() {
		
		@Override
		public Betting plugToBetting() {
			// TODO Auto-generated method stub
			BettingSoft bs = null;
			try {
				bs = new BettingSoft("varayut23");
				
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bs;
		}
		
		@Override
		public String getManagerPassword() {
			// TODO Auto-generated method stub
			return "varayut23";
		}
	};
//			BettingSoft bs = null;
//			@Override
//			public Betting plugToBetting() {
//				// TODO Auto-generated method stub
//				
//				try {
//					bs = new BettingSoft("rokhayagaye");
//				} catch (BadParametersException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return bs;
//			}
//			
//			@Override
//			public String getManagerPassword() {
//				// TODO Auto-generated method stub
//				return bs.getManagerPassword();
//			}
//		};
//		t.launchSubscribersValidationtest();
	}

}
