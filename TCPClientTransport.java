
import java.io.DataOutputStream;
import java.net.*;
import java.nio.ByteBuffer;

/**
 *
 * @author Blakeslee
 */
public class TCPClientTransport {    
    
    
    private Socket clientSocket;
    private final int MAX_PACKET_SIZE;
    DataOutputStream outToServer;

    public TCPClientTransport(String hostname, int port) throws Exception {

        clientSocket = new Socket(hostname, port);
        MAX_PACKET_SIZE = 500;
        outToServer = new DataOutputStream(clientSocket.getOutputStream());


    }

    public void sendBytes(byte[] bytes) throws Exception {
        outToServer.write(bytes);
    }

    public void sendFileSize(int fileSize) throws Exception {
        sendBytes(ByteBuffer.allocate(4).putInt(fileSize).array());
    }

    public int getPacketSize() {
        return MAX_PACKET_SIZE;
    }
}
