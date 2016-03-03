package simulator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarketData {
	
	private static final Logger log = LogManager.getLogger("monitor");
	
	private int[] depth = {
			1000000,
			2000000,
			5000000,
			10000000,
			20000000
		};
	
	public LocalTime time;
	public int size;
	public int move;
	public String instrument;
	
	 // 5 volume bands
	public BigDecimal bids[] = new BigDecimal[5];
	public BigDecimal offers[] = new BigDecimal[5];
	
	public MarketData()
	{
		time = LocalTime.now();
		
		// random market depth update
		size = Integer.valueOf(depth[new Random().nextInt(4)]);
		
		// simulate a random move, in pips, according to a probability density function
		move = Pips();
		
		instrument = "";
		
		for (int i = 0; i < 5; i ++) {
			bids[i] = new BigDecimal(0);
			offers[i] = new BigDecimal(0);
		}
		
		log.trace("new MarketData: " + getString(this));
	}
	
	public MarketData(String instrument, int size, BigDecimal[] bids, BigDecimal[] offers) {
		this.time = LocalTime.now();
		this.instrument = instrument;
		this.size = size;
		this.bids = bids;
		this.offers = offers;	
	}
	
	public String getString(MarketData m) {
		
		String data = "";
		
		data += m.time + ", size:";
		data += m.size + ", move:";
		data += m.move + ", ";
		data += m.instrument + " ";
		
		for (int i = 0; i < 5; i ++) {
			data += bids[i] + "/" + offers[i] + " ";
		}
		
		return data;
	}

	private int Pips() {
		
		/* 
		 * 50% probability the next tick is 1 pip
		 * 30% probability the next tick is 2 pips
		 * 10% probability the next tick is 3 pips
		 * 5% probability the next tick is 5 pips
		 * 3% probability the next tick is 10 pips
		 * 2% probability the next tick is 20 pips
		 */
		
		double probability = new Random().nextDouble();
		
		if (0 <= probability && probability < 0.5)
			return 1;
		else if (0.5 <= probability && probability < 0.8)
			return 2;
		else if (0.8 <= probability && probability < 0.9)
			return 3;
		else if (0.9 <= probability && probability < 0.95)
			return 5;
		else if (0.95 <= probability && probability < 0.98)
			return 10;
		else if (0.98 <= probability && probability < 1)
			return 20;
		
		return 0;
	}
}
