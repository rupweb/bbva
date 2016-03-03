package disruptorMarket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;

public class Replicator implements EventHandler<Event> 
{
	private static final Logger log = LogManager.getLogger("monitor");
	
	public byte[] buffer;
    public SocketAddress address;
    public int length;
	
    public Replicator() throws IOException, SocketException 
    {
    	log.info("In MarketData Replicator()");
    }		

	@Override
	public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception 
	{  	
    	log.debug("REPLICATE: " + event.message.getString(event.message));
		
		buffer = new byte[DisruptorMarket.BYTE_ARRAY_SIZE];
        length = 0;
    	
    	String endpointName = "127.0.0.1";
    	
    	// Create a non infinite replication loop!
    	int port = 9998;
    	   	
        final DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(1000);
  
        final InetAddress endpoint = InetAddress.getByName(endpointName);

        final DatagramPacket sendPacket = new DatagramPacket(buffer, length, endpoint, port);

        // Send to the replicator place
        clientSocket.send(sendPacket);
        
        // Close socket
        clientSocket.close();
	}
}
