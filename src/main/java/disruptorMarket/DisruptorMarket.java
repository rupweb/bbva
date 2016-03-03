package disruptorMarket;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import simulator.MarketData;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import disruptorVWAP.DisruptorVWAP;

public class DisruptorMarket 
{    
	private static final Logger log = LogManager.getLogger("monitor");
	
	private static final int RING_SIZE = 1*1024;
    static final int BYTE_ARRAY_SIZE = 1*1024;

    Disruptor<Event> disruptor;
    ExecutorService executor;
    Thread t;

	public DisruptorMarket() throws SocketException, IOException 
    {
		log.info("In DisruptorMarket()");
    }
	
    @SuppressWarnings("unchecked")
	public void start(DisruptorVWAP dP) throws SocketException, IOException 
    {	
    	int NUM_EVENT_PROCESSORS = 5;

        executor = Executors.newFixedThreadPool(NUM_EVENT_PROCESSORS);
        
        MarketEventFactory factory = new MarketEventFactory();

        log.info("Starting Disruptor Market & creating ring buffer of size: " + RING_SIZE);
        disruptor = new Disruptor<>(factory, RING_SIZE, executor, ProducerType.SINGLE, new BlockingWaitStrategy());
        // disruptor.handleEventsWith(new Journal(), new Replicator(), new LogicMarket(disruptor, dP));
        disruptor.handleEventsWith(new LogicMarket(disruptor, dP));
        disruptor.start();

        log.info("Disruptor Market listening...");
    }

    public void stop() throws Exception 
    {
        // early exit
        if (t == null) return;
        t.interrupt();
        t.join();

        log.info("Shutting down executor: " + executor.toString());
        executor.shutdown();
        t = null;
    }
    
	public void publish(MarketData m)
    {		
		log.debug("PUBLISH: " + m.getString(m));
		
		// Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();

        EventProducer producer = new EventProducer(ringBuffer);

        producer.onData(m);
    }
	
}
