
public class dl_client {

	public static void main(String[] args) {
		// String arguments are taken from the command line, where dl_client is run as:
		// dl_client hostname port filename

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String filename = args[2];

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
				if (packetsSent==10) { Thread.sleep(10); packetsSent=0; }
			}

			System.out.println("File transfer completed.");
			theFile.closeFile();


		}
		catch (Exception e) {
			e.printStackTrace();
		}






	}

}
