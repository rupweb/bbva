package disruptorVWAP;

import pricer.VWAP;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.EventTranslatorOneArg;

public class EventProducerWithTranslator 
{
	private final RingBuffer<Event> ringBuffer;

	public EventProducerWithTranslator(RingBuffer<Event> ringBuffer)
	{
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorOneArg<Event, VWAP> TRANSLATOR =
			new EventTranslatorOneArg<Event, VWAP>()
	{
		public void translateTo(Event event, long sequence, VWAP v)
		{
			event.set(v);
		}
	};

	public void onData(VWAP v)
	{
		ringBuffer.publishEvent(TRANSLATOR, v);
	}
}
