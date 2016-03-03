package disruptorMarket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import simulator.MarketData;
import pricer.VWAP;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import disruptorVWAP.DisruptorVWAP;

/**
 * Business logic goes here
 */
public class LogicMarket implements EventHandler<Event> 
{
	private static final Logger log = LogManager.getLogger("monitor");
	
	final RingBuffer<Event> ringbuffer;
    final Translator translator;
	private DisruptorVWAP dP;

    public LogicMarket(Disruptor<Event> output, DisruptorVWAP dP) 
    {
    	log.info("In LogicMarket()");
    	
        // translator will be used to write events into the buffer
        this.translator = new Translator();
        
        // get a hold of the ringbuffer, we can't publish direct to Disruptor as the DSL doesn't
        // provide a garbage-free two-arg publishEvent method
        this.ringbuffer = output.getRingBuffer();
        
        // Setup pricer disruptorMarket for price distribution (printing)
        this.dP = dP;
    }

    /// process events
    public void onEvent(Event event, long sequence, boolean endOfBatch)
    {
    	log.trace("EVENT:" + event.message.getString(event.message));
    	
    	MarketData m = event.message;
    	
    	// Calculate VWAP
    	VWAP v = new VWAP(m);
    		
    	// Publish to VWAP queue
    	dP.publish(v);
    }
}
