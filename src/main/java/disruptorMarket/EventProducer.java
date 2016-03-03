package disruptorMarket;

import simulator.MarketData;

import com.lmax.disruptor.RingBuffer;

public class EventProducer {
	
	private final RingBuffer<Event> ringBuffer;
	
	public EventProducer(RingBuffer<Event> ringBuffer)
	{
		this.ringBuffer = ringBuffer;
	}
	
    public void onData(MarketData message)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            Event event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
            event.set(message);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}
