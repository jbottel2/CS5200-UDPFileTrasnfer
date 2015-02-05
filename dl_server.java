import java.net.SocketTimeoutException;
import java.util.Arrays;

//CS 5200 dl_server
//Trevor Carter
//Jacob 
//Steve
//Note: received help at www.stackoverflow.com;


public class dl_server {

	public static final boolean DEBUG = true;

	public static void main(String[] args) {

		//used to get size of file from first udp message.
		int sizeOfFile = 0;
		ServerTransport receive_data;
		FileClass clientsFile;

		//amount of bytes copied
		int copied = 0;

		int port = 0;
		try {
		port = Integer.parseInt(args[0]);
		}
		catch (Exception e) { 
			if (DEBUG) e.printStackTrace();
			printUsage();
		}
		if (DEBUG) System.out.println("Listening on Port "+port);
		
		long beforeSeconds = 0;
		long afterSeconds = 0;
		byte [] bytesStorage = new byte [500];

		try{
			//The first message we receive should be the number of packets in 
			//file.

			while(true){
				receive_data = new ServerTransport(port);
				sizeOfFile = receive_data.receiveFileSize();
				if (DEBUG) System.out.println("Received file size: " + sizeOfFile + " bytes.");
				clientsFile = new FileClass("output.dat", 'w');
				copied = 0;

				beforeSeconds = System.currentTimeMillis();
				while(copied < sizeOfFile)
				{
					bytesStorage = receive_data.recieveBytes();
					if(bytesStorage != null)
					{
						// The last packet may have up to 499
						// extra bytes, so we must clip it.
						if (bytesStorage.length > sizeOfFile - copied)
						{
							bytesStorage = Arrays.copyOf(bytesStorage,sizeOfFile-copied);
						}
						clientsFile.writeBytes(bytesStorage);
						copied = clientsFile.getFileSize();
						System.out.print(copied + " bytes written. \r");
					}
					else if(bytesStorage == null)
					{
						break;
					}
				}
				afterSeconds = System.currentTimeMillis();
				if (DEBUG) System.out.println("Bytes Expected: " + sizeOfFile + ", Bytes Received: " + copied);
				System.out.println("File transfer complete. (" + convertToKbps(afterSeconds-beforeSeconds, copied) + ")");

				//close file before repeat
				clientsFile.closeFile();
				receive_data.closeConnection();

			}
		}
		catch(SocketTimeoutException e) {
			if (DEBUG) System.out.println("Socket timed out");
		}
		catch(Exception e){
			System.out.println("Error: " + e);
			if (DEBUG) e.printStackTrace();
		}

	}

	public static String convertToKbps(long elapsedMs, int numBytes) {
		if (elapsedMs == 0) return "" + numBytes/1000 + " Kbps";
		return "" + numBytes/elapsedMs + " Kbps";
	}
	
	public static void printUsage() {
	    System.out.println("Usage:");
		System.out.println("dl_server port");
		System.exit(0);
	}
}
