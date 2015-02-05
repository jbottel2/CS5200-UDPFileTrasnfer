
public class dl_client {

	public static final boolean DEBUG = true;

	public static void main(String[] args) {

		String hostname = null;
		int port = 0;
		String filename = null;

		try {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
			filename = args[2];
		}
		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			printUsage();
		}

		// Create a client transport layer.
		try {
			ClientTransport ct = new ClientTransport(hostname, port);
			FileClass theFile = new FileClass(filename,'r');

			// Send the file size to the server before initiating transfer.
			int fileSize = theFile.getFileSize();
			ct.sendFileSize(fileSize);

			// The amount of bytes we will send at a time is the
			// amount that ClientTransport tells us is the max packet size.
			int amountToSend = ct.getPacketSize();

			int packetsSent = 0;
			while (theFile.hasBytes()) {
				// Send the byte arrays equal to the max packet size.
				ct.sendBytes(theFile.getBytes(amountToSend));
				packetsSent++;
				// Slow the flow of packets to prevent full buffers.
				if (packetsSent==10) { Thread.sleep(5); packetsSent=0; }
			}

			if (DEBUG) System.out.println("");
			System.out.println("File transfer completed.");
			theFile.closeFile();


		}
		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			else System.out.println("An error occured.");
		}
	}

	public static void printUsage() {
		System.out.println("Usage:");
		System.out.println("dl_client hostname port filename");
		System.exit(0);
	}
}
