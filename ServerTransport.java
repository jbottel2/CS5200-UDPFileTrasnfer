//CS 5200 ServerTransport

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class ServerTransport{

    public static final boolean DEBUG=true;
    int numberOfBytes;
    DatagramSocket server;
    DatagramPacket received;
    DatagramPacket fileSize;
    byte [] receiveFileData;
    byte [] receiveFileSizeData;
    
    ServerTransport(int port) throws Exception{
        
        server = new DatagramSocket(port);
        //www.stackoverflow.com forum helped out here
        receiveFileData = new byte[500];
        receiveFileSizeData = new byte[4];
        received = new DatagramPacket(receiveFileData, receiveFileData.length);
        fileSize = new DatagramPacket(receiveFileSizeData, receiveFileSizeData.length);
        numberOfBytes =0;
    }
    
    byte [] recieveBytes() throws Exception{
        
        //www.stackoverflow.com helped out here
        server.setSoTimeout(2500);
        try{
            server.receive(received);
        }
        catch(SocketTimeoutException E)
        {
            return null;
        }
        
        return received.getData();
        
    }
    
    //help from stackoverflow.com forum
    int receiveFileSize() throws Exception{
        //get fileSize from client
        server.setSoTimeout(0);
        server.receive(fileSize);  
        numberOfBytes = ByteBuffer.wrap(fileSize.getData()).getInt();
        return numberOfBytes;
    }

    void closeConnection() throws IOException {
	    server.close();
    }
     
}