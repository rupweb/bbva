package disruptorVWAP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pricer.VWAP;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Business logic goes here
 */
public class LogicVWAP implements EventHandler<Event> 
{
	private static final Logger log = LogManager.getLogger("monitor");
	
	final RingBuffer<Event> ringbuffer;
    final Translator translator;

    public LogicVWAP(Disruptor<Event> output)
    {
    	log.info("In LogicVWAP()");
    	
        // translator will be used to write events into the buffer
        this.translator = new Translator();
        
        // get a hold of the ringbuffer, we can't publish direct to Disruptor as the DSL doesn't
        // provide a garbage-free two-arg publishEvent method
        this.ringbuffer = output.getRingBuffer();
    }

    /// process events
    public void onEvent(Event event, long sequence, boolean endOfBatch)
    {    	
    	VWAP v = event.price;
    	
    	// Print out the VWAP price
    	System.out.print(v.time + " " + v.instrument + " " + v.bid + "/" + v.offer + "\r\n");
    }
}
