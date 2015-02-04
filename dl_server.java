//CS 5200 DL_server
//Trevor Carter
//Jacob 
//Steve
//Note: received help at www.stackoverflow.com;

public class dl_server {

    public static void main(String[] args) {
        
        //used to get size of file from first udp message.
        int sizeOfFile = 0;
        ServerTransport receive_data;
        FileClass clientsFile;
        
        //amount of bytes copied
        int copied = 0;
        long beforeSeconds = 0;
        long afterSeconds = 0;
        byte [] bytesStorage;
        
        try{
            receive_data = new ServerTransport();
            //The first message we recieve should be the number of packets in 
            //file.
            while(true){
                sizeOfFile = receive_data.receiveFileSize();
                bytesStorage = new byte[500];
                clientsFile = new FileClass("output.dat", 'w');
                copied = 0;
                
                beforeSeconds = System.currentTimeMillis();
                while(copied < sizeOfFile)
                {
                    bytesStorage = receive_data.recieveBytes();
                    if(bytesStorage != null)
                    {
                    	//if (bytesStorage.length()+copied > sizeOfFile) {
                    	//	bytesCopy = 
                    	//}
                        clientsFile.writeBytes(bytesStorage);
                        copied = clientsFile.getFileSize();
                    }
                    else if(bytesStorage == null)
                    {
                        break;
                    }
                }
                afterSeconds = System.currentTimeMillis();
                System.out.println("size of File sent: " + sizeOfFile + " size of File copied" + copied);
                System.out.println("File Copied(kbps): " + (copied)/(afterSeconds - beforeSeconds));
                
                //close file before repeat
                clientsFile.closeFile();
        
            }
        }
        catch(Exception e){
            System.out.println("Error:" + e);
        }
         
    }
}