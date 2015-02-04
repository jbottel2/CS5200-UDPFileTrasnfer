//CS 5200 ServerTransport

import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;

public class TCPServerTransport{

    int numberOfBytes;
    ServerSocket server;
    Socket socket;
    InputStream in;
    byte [] receiveFileData;
    byte [] receiveFileSizeData;
    
    TCPServerTransport(int port) throws Exception{
        
        server = new ServerSocket(port);
        Socket socket = server.accept();
        in = socket.getInputStream();
        //www.stackoverflow.com forum helped out here
        receiveFileData = new byte[500];
        receiveFileSizeData = new byte[4];
        numberOfBytes =0;
        
    }
    
    byte [] recieveBytes(int amount) throws Exception{
        //www.stackoverflow.com helped out here
    	byte[] theseBytes = new byte[amount];
    	in.read(theseBytes, 0, amount);
    	return theseBytes;
    }
    
    //help from stackoverflow.com forum
    int receiveFileSize() throws Exception{
        
        //get fileSize from client
    	byte[] fileSize = new byte[4];
        in.read(fileSize,0,4);
        numberOfBytes = ByteBuffer.wrap(fileSize).getInt();

        return numberOfBytes;
    }
     
}