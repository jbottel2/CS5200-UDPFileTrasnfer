//CS 5200 ServerTransport

import java.net.*;
import java.nio.ByteBuffer;

public class ServerTransport{

    int numberOfBytes;
    DatagramSocket server;
    DatagramPacket received;
    DatagramPacket fileSize;
    byte [] receiveFileData;
    byte [] receiveFileSizeData;
    
    ServerTransport() throws Exception{
        
        server = new DatagramSocket(15200);
        //www.stackoverflow.com forum helped out here
        receiveFileData = new byte[500];
        receiveFileSizeData = new byte[4];
        received = new DatagramPacket(receiveFileData, receiveFileData.length);
        fileSize = new DatagramPacket(receiveFileSizeData, receiveFileSizeData.length);
        numberOfBytes =0;
        
    }
    
    byte [] recieveBytes() throws Exception{
        //www.stackoverflow.com helped out here
    	System.out.println("receiving");
        server.receive(received);
        return received.getData();
    }
    
    //help from stackoverflow.com forum
    int receiveFileSize() throws Exception{
        
        //get fileSize from client
        server.receive(fileSize);
        server.setSoTimeout(2500);
        
        numberOfBytes = ByteBuffer.wrap(fileSize.getData()).getInt();

        return numberOfBytes;
    }
     
}