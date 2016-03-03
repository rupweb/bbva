package venues;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import simulator.MarketData;

public class Fxall {
	
	private static final Logger log = LogManager.getLogger("monitor");

	public Fxall() {
		
	}

	public MarketData update(String instrument, MarketData m) {
		
		log.trace("In Fxall.update with instrument: " + instrument);
		
		BigDecimal tick;
		
		// Random tick up or down
		int direction = new Random().nextInt(2);
		if (direction == 0)
			tick = new BigDecimal(m.move).negate();
		else
			tick = new BigDecimal(m.move);
		
		m = All.update(m, instrument, tick);	
		
		return m;
		
	}
}

