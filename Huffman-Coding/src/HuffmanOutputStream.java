import java.io.*;

public class HuffmanOutputStream {

	private DataOutputStream d;
	int count = 0;
	int b = 0;
 
	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		}
		catch (IOException e) {
		}
	}
	
	//recieves 1 or 0 and makes bytes to write to file
	public void writeBit(char bit) {
		 //PRE:bit == '0' || bit == '1'
		 try {
			 b = b * 2 + (bit - '0');
			 count++;
			 
			 if(count == 8) {
				 d.writeByte(b);
				 b = 0;
				 count = 0;
			 }
		 }
		 catch (IOException e) {
		 }

	}
	
	public void close() {
		 //write final byte (if needed)
		 //close the DataOutputStream
		 try {
			 if(count != 0) {
				 b = b << (8 - count);
				 d.writeByte(b);
			 }
			 d.close();
		 }
		 catch (IOException e) {
		 }
	} 
}