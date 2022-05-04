import java.io.*;
import java.util.*;

public class HuffmanEncode {
	
	//global variable count, so it can be updated in multiple methods
	private int count;
	
	public HuffmanEncode(String in, String out) {
		//Implements the main flow of your program
		//in is the name of the source file
		//out is the name of the output file
		//Add private methods and instance variables as needed
		
		//Create frequency array and set each spot in the array to 0
		int[] freq = new int[128];
		for(int i = 0; i < 128; i++) {
			freq[i] = 0;
		}
		
		//initialize global count variable
		count = 0;
		
		//get frequency of each character in file
		freq = getFrequency(in, freq, count);
		
		//create BinaryHeap with length equal to how many different characters are in the file + 1 since we don't spot 0
		BinaryHeap binHeap = new BinaryHeap(length(freq) + 1);
		
		//create HuffmanTree using binaryheap priority queue
		HuffmanTree huffTree = buildTree(binHeap, freq);
		
		//array of paths (0's and 1's) to get to each character in the huffmanTree
		String[] paths = findPath(huffTree);
		
		//create huffman output stream, with name of encoded file, string representation of huffman tree
		//and total number of characters in the file
		HuffmanOutputStream huffOut = new HuffmanOutputStream(out, huffTree.toString(), count);
		
		//calls method that prints each bit to file
		toByte(huffOut, paths, in);
	}
	
	//gets frequency of each character in the file
	private int[] getFrequency(String in, int[] freq, int c) {
		count = c;
		
		try {
			FileReader fr = new FileReader(new File(in));
			BufferedReader reader = new BufferedReader(fr);
			int fChar = reader.read();
			
			while(fChar != -1) {
				freq[fChar]++;
				count++;
				fChar = reader.read();
			}
		} catch(Exception e) {
		}
	
		return freq;
	}
	
	//finds how many different characters are in the array
	private int length(int[] freq) {
		int numChar = 0;
		for(int i = 0; i < 128; i++) {
			if(freq[i] != 0) {
				numChar++;
			}
		}
		return numChar;
	}
	
	//builds huffman tree from binary heap priority queue
	private HuffmanTree buildTree(BinaryHeap binHeap, int[] freq) {
		HuffmanTree huffNode = null;
		
		//makes each character that appears in file an individual node and inserts that node
		//with associated priority in to binary heap
		for(int i = 0; i < 128; i++) {
			if(freq[i] != 0) {
				char letter = (char) i;
				huffNode = new HuffmanTree(letter);
				binHeap.insert(freq[i], huffNode);
			}
		}
		
		int p1 = 0;
		int p2 = 0;
		int merge = 0;
		HuffmanTree right = null;
		HuffmanTree left = null;
		char empty = 128;
		
		//builds tree with priority queue. Merges priority and then inserts new huffman tree with associated
		//merged priority into the binary heap
		while(binHeap.getSize() > 1) {
			p1 = binHeap.getMinPriority();
			
			if(p1 == -1) {
				break;
			}
			
			right = binHeap.getMinTree();
			binHeap.removeMin();
			
			p2 = binHeap.getMinPriority();
			
			if(p2 == -1) {
				break;
			}
			
			left = binHeap.getMinTree();
			binHeap.removeMin();
			
			huffNode = new HuffmanTree(left, empty, right);
			
			
			merge = p1 + p2;
			binHeap.insert(merge, huffNode);
		}
		
		return huffNode;
	}
	
	//returns array with paths to leaves
	private String[] findPath(HuffmanTree huffTree) {
		String[] p = huffTree.pathsToLeaves();
		return p;
	}
	
	//sends bits of each charater one by one to writeBit method
	private void toByte(HuffmanOutputStream huffOut, String[] pathArr, String in) {
		try {
			FileReader fr = new FileReader(new File(in));
			BufferedReader reader = new BufferedReader(fr);
			int cha = reader.read();
			
			while(cha != -1) {
				String temp = pathArr[cha];
						while(temp.length() != 0) {
							huffOut.writeBit(temp.charAt(0));
							temp = temp.substring(1);
						}
				cha = reader.read();;
			}	
		} catch(Exception e) {
		}
		huffOut.close();
	}
	
	public static void main(String args[]) {
		//args[0] is the name of the source file
		//args[1] is the name of the output file
		new HuffmanEncode(args[0], args[1]);
		//do not change anything here
	}
}