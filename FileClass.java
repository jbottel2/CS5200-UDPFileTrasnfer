import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileClass {
	
	public static final boolean DEBUG = true;
	File currentFile;
	char mode;
	FileInputStream inputStream;
	FileOutputStream outputStream;
	int fileSize;
	int byteLocation;
	

	FileClass(String filename, char mode) throws IOException	{
		currentFile = new File(filename);
		this.mode = mode;
		if (this.mode=='w') {
            currentFile.createNewFile();
            if (DEBUG) System.out.println("Opened file for writing.");
			outputStream = new FileOutputStream(currentFile);
			fileSize = (int) currentFile.length();
		}
		else if (this.mode=='r') {
			inputStream = new FileInputStream(currentFile);
			fileSize = (int) currentFile.length();
			if (DEBUG) System.out.println("Opened file for reading.");
			if (DEBUG) System.out.println("The file size is " + fileSize + " bytes.");
			byteLocation = 0;
		}
	}
	
	public int getFileSize() {
		return fileSize;
	}
	
	public boolean hasBytes() {
		if (byteLocation < fileSize) return true;
		return false;
	}
	
	public byte[] getBytes(int length) throws IOException {
		if (byteLocation + length > fileSize) {
			length = fileSize-byteLocation;
		}
		byte[] theseBytes = new byte[length];
		if (DEBUG) System.out.print("Current Location: "+ byteLocation + ". Reading " + length + " bytes. \r");
		inputStream.read(theseBytes, 0, length);
		byteLocation += length;
		return theseBytes;
	}

	public void writeBytes(byte[] bytes) throws IOException {
		outputStream.write(bytes);
		fileSize += bytes.length;
	}
	
	public void closeFile() throws IOException {
		if (mode=='w') outputStream.close();
		if (mode=='r') inputStream.close();
	}
}
