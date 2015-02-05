import java.net.*;
import java.nio.ByteBuffer;

/**
 *
 * @author Blakeslee
 */
public class ClientTransport {

    private DatagramSocket clientSocket;
    private InetAddress serverIPAddress;
    private int serverPort;
    private final int MAX_PACKET_SIZE = 500;
    
    public ClientTransport(String hostname, int port) throws Exception{
        
        clientSocket = new DatagramSocket();
        serverIPAddress = InetAddress.getByName(hostname);
        serverPort = port; 
        
    }
    
    public void sendBytes(byte[] bytes) throws Exception{
        DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,serverIPAddress,serverPort);
        clientSocket.send(sendPacket);  
       
    }
    
    public void sendFileSize(int fileSize) throws Exception {
    	sendBytes(ByteBuffer.allocate(4).putInt(fileSize).array());
    }

    public int getPacketSize(){
        return MAX_PACKET_SIZE;        
    }
    
   }
