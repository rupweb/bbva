package disruptorVWAP;

import pricer.VWAP;

import com.lmax.disruptor.RingBuffer;

public class EventProducer {
	
	private final RingBuffer<Event> ringBuffer;
	
	public EventProducer(RingBuffer<Event> ringBuffer)
	{
		this.ringBuffer = ringBuffer;
	}
	
    public void onData(VWAP v)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            Event event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
            event.set(v);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}
