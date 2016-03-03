package simulator;

import java.time.LocalDateTime;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import venues.Ebs;
import venues.Fxall;
import venues.Hotspot;
import venues.Lava;
import venues.Reuters;
import disruptorMarket.DisruptorMarket;

public class Venue {
	
	private static final Logger log = LogManager.getLogger("monitor");
	
	public enum Instrument {
		   EURUSD,
		   GBPUSD,
		   USDJPY,
		   EURJPY,
		   EURGBP,
		   AUDUSD
		}
	
	public enum Venues {
		EBS,
		REUTERS,
		FXALL,
		HOTSPOT,
		LAVA
	}
	
	public Venue() {
		// null constructor for tests only
	}
	
	public Venue(DisruptorMarket marketD) throws InterruptedException {
		
		String instrument, venue;
		
		LocalDateTime start = LocalDateTime.now();
		System.out.println("Start producing market updates @ " + start);
		
		for(int i = 0; i < 100000; i++) {
			instrument = Instrument.values()[new Random().nextInt(Instrument.values().length)].toString();
			venue = Venues.values()[new Random().nextInt(Venues.values().length)].toString();
			
			marketD.publish(MarketDataUpdate(instrument, venue));
			
			// Random thread sleep from 0 to 100 milliseconds
			// Thread.sleep(new Random().nextInt(100));
		}
		
		System.out.println("Started producing market updates @ " + start);		
		System.out.println("Finished producing market updates @ " + LocalDateTime.now());
	}

	public MarketData MarketDataUpdate(String instrument, String venue) {
		
		log.debug("In MarketDataUpdate with instrument: " + instrument + ", venue: " + venue);
		
		MarketData m = new MarketData();
		
		switch (venue) {
			case "EBS":
				m = new Ebs().update(instrument, m);
				break;
				
			case "REUTERS":
				m = new Reuters().update(instrument, m);
				break;
			
			case "FXALL":
				m = new Fxall().update(instrument, m);
				break;
				
			case "HOTSPOT":
				m = new Hotspot().update(instrument, m);
				break;
				
			case "LAVA":
				m = new Lava().update(instrument, m);
				break;		
		}
			
		return m;
	}
}
