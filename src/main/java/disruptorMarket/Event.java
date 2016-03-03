package disruptorMarket;

import simulator.MarketData;

/**
 * Value event
 * This is the value event we will use to pass data between threads
 */
public class Event  
{
    protected MarketData message;
    
	public void set(MarketData m) 
	{
		this.message = m;
	}
}
