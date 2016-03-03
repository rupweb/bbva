package pricer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import simulator.MarketData;

public class VWAP 
{
	private static final Logger log = LogManager.getLogger("monitor");
	
	public LocalTime time;
	public String instrument;
	public BigDecimal bid;
	public BigDecimal offer;

	public VWAP(MarketData m) {
			
		BigDecimal band1 = m.bids[0].multiply(new BigDecimal(1000000)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal band2 = m.bids[1].multiply(new BigDecimal(2000000)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal band3 = m.bids[2].multiply(new BigDecimal(5000000)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal band4 = m.bids[3].multiply(new BigDecimal(10000000)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal band5 = m.bids[4].multiply(new BigDecimal(20000000)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal quantitySum = new BigDecimal(38000000);
		
		bid = band1.add(band2).add(band3).add(band4).add(band5).divide(quantitySum, 5, RoundingMode.HALF_UP);
		
		band1 = m.offers[0].multiply(new BigDecimal(1000000)).setScale(5, RoundingMode.HALF_UP);
		band2 = m.offers[1].multiply(new BigDecimal(2000000)).setScale(5, RoundingMode.HALF_UP);
		band3 = m.offers[2].multiply(new BigDecimal(5000000)).setScale(5, RoundingMode.HALF_UP);
		band4 = m.offers[3].multiply(new BigDecimal(10000000)).setScale(5, RoundingMode.HALF_UP);
		band5 = m.offers[4].multiply(new BigDecimal(20000000)).setScale(5, RoundingMode.HALF_UP);
		
		offer = band1.add(band2).add(band3).add(band4).add(band5).divide(quantitySum, 5, RoundingMode.HALF_UP);
		
		time = LocalTime.now();
		
		instrument = m.instrument;
	}

	public VWAP() {
		log.trace ("VWAP constructor");
	}
}


