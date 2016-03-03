package disruptorVWAP;

import pricer.VWAP;

import com.lmax.disruptor.EventFactory;

public class MarketEventFactory implements EventFactory<Event>
{
	@Override
	public Event newInstance() 
	{
        Event e = new Event();
        e.price = new VWAP();
        return e;
	}
}
