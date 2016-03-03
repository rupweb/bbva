package disruptorMarket;

import simulator.MarketData;

import com.lmax.disruptor.EventFactory;

public class MarketEventFactory implements EventFactory<Event>
{
	@Override
	public Event newInstance() 
	{
        Event e = new Event();
         e.message = new MarketData();	
        return e;
	}
}
