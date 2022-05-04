import java.io.*;
import java.util.*;

public class HuffmanDecode {
	public HuffmanDecode(String in, String out) {
		//implements the Huffman Decode Algorithm
		//Add private methods and instance variables as needed
		
		//creates Huffman input stream object
		HuffmanInputStream InStream = new HuffmanInputStream(in);

		//rebuilt tree from string represention in encoded file
		HuffmanTree afterTree = new HuffmanTree(InStream.getTree(), (char) 128);

		//decodes encoded file
		buildFile(InStream, afterTree, out);
	}
	
	//rebuilds file from encoded bits
	private void buildFile(HuffmanInputStream InStream, HuffmanTree afterTree, String out) {
		int bit = -1;
		int count = 0;
		
		//total number of characters in file
		int total = InStream.getTotalChars();
		
		try {
			FileWriter fw = new FileWriter(out);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			afterTree.moveToRoot();

			bit = InStream.readBit();

			//move through tree depends on bits until a leaf is reached
			while(count != total) {
				if(bit == 0) {
					afterTree.moveToLeft();
				}	
				else if(bit == 1){
					afterTree.moveToRight();
				}
				
				//at leaf, write charcter to decoded file
				if(afterTree.atLeaf() == true) {
					pw.write(afterTree.current());
					bit = InStream.readBit();
					count++;
					afterTree.moveToRoot();
				} else {
					bit = InStream.readBit();
				}	
			}
			
			pw.close();
			InStream.close();
			
		} catch(Exception e) {		
		}
		
	}
	
	public static void main(String args[]) {
		//args[0] is the name of a input file (a file created by Huffman Encode)
		//args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		//do not add anything here
	}
}