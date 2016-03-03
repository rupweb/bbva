package instruments;

import java.math.BigDecimal;
import java.math.RoundingMode;

import simulator.MarketData;

public class EURJPY {
	
	public enum Spreads {
		   band1bid(spread(11)),
		   band1offer(spread(12)),
		   band2bid(spread(22)),
		   band2offer(spread(24)),
		   band3bid(spread(38)),
		   band3offer(spread(40)),
		   band4bid(spread(45)),
		   band4offer(spread(50)),
		   band5bid(spread(60)),
		   band5offer(spread(70));
		      
		   private final BigDecimal spread;
		   
		   Spreads (BigDecimal spread) {
			   this.spread = spread;
		   }

		   public BigDecimal getSpread() {
			   return this.spread;
		   }
		}

	public static BigDecimal mid = BigDecimal.valueOf(124);
	
	 // 5 volume bands
	private static BigDecimal zero = BigDecimal.ZERO;
	public static BigDecimal bids[] = new BigDecimal[] {zero, zero, zero, zero, zero};
	public static BigDecimal offers[] = new BigDecimal[] {zero, zero, zero, zero, zero};
	
	public static MarketData depth(int size) {
		
		if (size < 1000001)
			bids[0] = mid.add(Spreads.band1bid.getSpread().negate());
			offers[0] = mid.add(Spreads.band1offer.getSpread());
		
		if (size < 2000001)
			bids[1] = mid.add(Spreads.band2bid.getSpread().negate());
			offers[1] = mid.add(Spreads.band2offer.getSpread());

		if (size < 5000001)
			bids[2] = mid.add(Spreads.band3bid.getSpread().negate());
			offers[2] = mid.add(Spreads.band3offer.getSpread());

		if (size < 10000001)
			bids[3] = mid.add(Spreads.band4bid.getSpread().negate());
			offers[3] = mid.add(Spreads.band4offer.getSpread());

		if (size < 20000001)
			bids[4] = mid.add(Spreads.band5bid.getSpread().negate());
			offers[4] = mid.add(Spreads.band5offer.getSpread());
			
		return new MarketData("EURJPY", size, bids, offers);
	}
	
	private static BigDecimal spread(int i) {
		return new BigDecimal(i).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
	}
}
