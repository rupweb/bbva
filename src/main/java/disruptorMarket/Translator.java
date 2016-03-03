package disruptorMarket;

import simulator.MarketData;

import com.lmax.disruptor.EventTranslator;

/**
 * Pushes an output event onto the target disruptor
 * <p>
 * This uses the EventTranslatorOneArg which has a special publishEvent facility on the ringbuffer
 * to avoid generating any garbage
 */

public class Translator implements EventTranslator<MarketData> 
{
	public void translateTo(MarketData event, long sequence) 
	{
		System.out.println("In Translator.translateTo()");
	}

}
