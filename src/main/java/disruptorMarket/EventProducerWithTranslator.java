package disruptorMarket;

import simulator.MarketData;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.EventTranslatorOneArg;

public class EventProducerWithTranslator 
{
	private final RingBuffer<Event> ringBuffer;

	public EventProducerWithTranslator(RingBuffer<Event> ringBuffer)
	{
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorOneArg<Event, MarketData> TRANSLATOR =
			new EventTranslatorOneArg<Event, MarketData>()
	{
		public void translateTo(Event event, long sequence, MarketData m)
		{
			event.set(m);
		}
	};

	public void onData(MarketData m)
	{
		ringBuffer.publishEvent(TRANSLATOR, m);
	}
}
