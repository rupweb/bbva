package disruptorVWAP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;

public class Journal implements EventHandler<Event> 
{
	private static final Logger log = LogManager.getLogger("monitor");

    public Journal()
    {
    	log.info("In MarketData Journal()");
    }	
	
	@Override
	public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception 
	{			
    	log.debug("JOURNAL: " + event.price.time + ", " + 
    						event.price.instrument + ", " + 
    						event.price.bid + "/" +   			
    						event.price.offer);
    						
    	// TODO: Get a byte stream of the event to disk
	}
}
