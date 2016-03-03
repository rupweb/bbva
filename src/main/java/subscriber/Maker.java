package subscriber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import disruptorMarket.DisruptorMarket;
import disruptorVWAP.DisruptorVWAP;
import simulator.Venue;

public class Maker {

	private static final Logger log = LogManager.getLogger("monitor");

	public static void main(String[] args) throws Exception 
    {
		log.info("In main()");
    	
    	// The windows newline in logs requires correct separator
    	System.setProperty("line.separator", "\r\n");

    	// Set up market side and price side Disruptors
    	DisruptorMarket marketD = new DisruptorMarket(); 
    	DisruptorVWAP vwapD = new DisruptorVWAP(); 
    	
    	// Start the disruptors
		// Publishing to the disruptors can then begin
		try
	    {        
	    	vwapD.start();
			marketD.start(vwapD);

			log.info("Disruptors started");
	    }
	    catch (Exception e)
	    {
	    	vwapD.stop();
	    	marketD.stop();

	    	log.error("==DISRUPTORS ERROR==");                
    		StringWriter errors = new StringWriter();
    		e.printStackTrace(new PrintWriter(errors));
    		System.out.println("ERROR: " + errors);
	    }
		
		System.out.println("");
		System.out.println("Disruptor queues ready. Hit enter to start simulation");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true)
	    {
	    	try {
				scanner.nextLine();
				startSimulator(marketD);
	    	}
	    	catch(Exception e)
	    	{
	    		StringWriter errors = new StringWriter();
	    		e.printStackTrace(new PrintWriter(errors));
	    		System.out.println("ERROR: " + errors);
	    	}
	    }  	
    }

	static void startSimulator(DisruptorMarket mD)
    {       
		log.info("Starting simulator @ " + LocalDateTime.now()); 
		
		try
        {			          	            	
			new Venue(mD);
		}
        catch (Exception e)
        {
        	log.error("==SIMULATOR FATAL ERROR==");                
        	log.error(e.toString());
        }
		
		log.info("Finished simulator @ " + LocalDateTime.now()); 
    }
}
