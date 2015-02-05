//CS 5200 ServerTransport

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;

public class TCPServerTransport {

	public static final boolean DEBUG=true;
    int numberOfBytes;
    ServerSocket server;
    Socket socket;
    InputStream in;
    byte [] receiveFileData;
    byte [] receiveFileSizeData;
    
    TCPServerTransport(int port) throws Exception{
        
        server = new ServerSocket(port);
        socket = server.accept();
        in = socket.getInputStream();
        //www.stackoverflow.com forum helped out here
        receiveFileData = new byte[500];
        receiveFileSizeData = new byte[4];
        numberOfBytes =0;
        
    }
    
    byte [] recieveBytes() throws Exception{
        //www.stackoverflow.com helped out here
    	byte[] theseBytes = new byte[500];
    	in.read(theseBytes, 0, 500);
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
    
    void closeConnection() throws IOException {
    	socket.close();
    	server.close();
    }
     
}