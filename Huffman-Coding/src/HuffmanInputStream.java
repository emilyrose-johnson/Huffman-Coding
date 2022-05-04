import java.io.*;

public class HuffmanInputStream {

	private String tree;
	private int totalChars;
	private DataInputStream d;
	private int count;
	int by[];
	
	public HuffmanInputStream(String filename) {

		count = 8;
		by = new int[8];
		
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();
			
		}
		catch (IOException e) {
		}
	}
	
	public int readBit() {
		//returns the next bit is the file
		 //the value returned will be either a 0 or a 1
		int b = 0;
		int bit = -1;
		 try {
			 
			//reads in next byte from file and puts them into an array starting at position 7 
			if(count == 8) {
				b = d.readUnsignedByte();
				for(int i = 7; i >= 0; i--) {
					by[i] = b % 2;
					b = b / 2;
				}
				count = 0;
			}
			
			//set equal to next bit in array
			bit = by[count];
			count++;
			

		 }
		 catch (IOException e) {
		 }
			
		return bit;
	}
	
	public String getTree() {
		return tree;
	}
	
	public int getTotalChars() {
		//return the character count read from the file
		return totalChars;
	}
	
	public void close() {
		//close the DataInputStream
		try {
			d.close();
		} catch (IOException e) {
		}
	}
}