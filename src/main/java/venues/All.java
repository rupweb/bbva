package venues;

import instruments.AUDUSD;
import instruments.EURGBP;
import instruments.EURJPY;
import instruments.EURUSD;
import instruments.GBPUSD;
import instruments.USDJPY;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import simulator.MarketData;

public class All {
	
	private static final Logger log = LogManager.getLogger("monitor");

	public static MarketData update(MarketData m, String instrument, BigDecimal tick) {
		
		log.trace("tick:" + tick);
		
		switch (instrument) {
		case "EURUSD":
			log.trace("EURUSD.mid:" + EURUSD.mid);
			EURUSD.mid = EURUSD.mid.add(tick.divide(new BigDecimal(10000)));
			log.trace("EURUSD.mid:" + EURUSD.mid);
			m = EURUSD.depth(m.size);
			break;
		case "GBPUSD":
			log.trace("GBPUSD.mid:" + GBPUSD.mid);
			GBPUSD.mid = GBPUSD.mid.add(tick.divide(new BigDecimal(10000)));
			log.trace("GBPUSD.mid:" + GBPUSD.mid);
			m = GBPUSD.depth(m.size);
			break;
		case "USDJPY":
			log.trace("USDJPY.mid:" + USDJPY.mid);
			USDJPY.mid = USDJPY.mid.add(tick.divide(new BigDecimal(100)));
			log.trace("USDJPY.mid:" + USDJPY.mid);
			m = USDJPY.depth(m.size);
			break;
		case "EURJPY":
			log.trace("EURJPY.mid:" + EURJPY.mid);
			EURJPY.mid = EURJPY.mid.add(tick.divide(new BigDecimal(100)));
			log.trace("EURJPY.mid:" + EURJPY.mid);
			m = EURJPY.depth(m.size);
			break;
		case "EURGBP":
			log.trace("EURGBP.mid:" + EURGBP.mid);
			EURGBP.mid = EURGBP.mid.add(tick.divide(new BigDecimal(10000)));
			log.trace("EURGBP.mid:" + EURGBP.mid);
			m = EURGBP.depth(m.size);
			break;
		case "AUDUSD":
			log.trace("AUDUSD.mid:" + AUDUSD.mid);
			AUDUSD.mid = AUDUSD.mid.add(tick.divide(new BigDecimal(10000)));
			log.trace("AUDUSD.mid:" + AUDUSD.mid);
			m = AUDUSD.depth(m.size);
			break;
		}
		
		// Add in the tick size
		m.move = tick.intValue();
		
		return m;
	}
	
}
