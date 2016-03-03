package disruptorVWAP;

import pricer.VWAP;

/**
 * Value event
 * This is the value event we will use to pass data between threads
 */
public class Event  
{
    protected VWAP price;
    
	public void set(VWAP v) 
	{
		this.price = v;
	}
}
