import java.util.*;

public class HuffmanTree {
//DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		public char data;
		private Node right;

		private Node(Node L, char d, Node R) {
			left = L;
			data = d;
			right = R;
		}
	}
	
	private Node root;
	private Node current; //this value is changed by the move methods
	
	public HuffmanTree() {
		 root = null;
		 current = null;
	}
	
	public HuffmanTree(char d) {
		//makes a single node tree
		root = new Node(null, d, null);
	}
	
	//rebuilds tree after file has been encoded. Rebuilds from string representaton of tree from encoded file using stack
	public HuffmanTree(String t, char nonLeaf) {
		//Assumes t represents a post order representation of the tree as discussed
		// in class
		//nonLeaf is the char value of the data in the non-leaf nodes
		//in the following I will use (char) 128 for the non-leaf value
		Stack<Node> sta = new Stack<>();
		 Node right;
		 Node left;
		 
		 while (t.length() != 0) {
			 char cha = t.charAt(0);
			 if(cha == 128) {
				 right = sta.pop();
				 left = sta.pop();
				 Node n = new Node(left, nonLeaf, right);
				 sta.push(n);
			 } else {
				 Node n = new Node(null, cha, null);
				 sta.push(n);
			 }
			 
			 t = t.substring(1);	 
		 }	 

		 root = sta.pop();
	}
	
	public HuffmanTree(HuffmanTree b1, char d, HuffmanTree b2) {
		//makes a new tree where b1 is the left subtree and b2 is the right subtree
		//d is the data in the root
		root = new Node(b1.root, d, b2.root);
		current = root;
	}
	
	//the move methods to traverse the tree
	//the move methods change the value of current
	//use these in the decoding process
	
	public void moveToRoot() {
	 //change current to reference the root of the tree
		current = root;
	}
	
	public void moveToLeft() {
	 //PRE: the current node is not a leaf
	 //change current to reference the left child of the current node
		current = current.left;
	}
	
	public void moveToRight() {
	 //PRE: the current node is not a leaf
	 //change current to reference the right child of the current node
		current = current.right;
	}
	
	public boolean atRoot() {
		 //returns true if the current node is the root otherwise returns false
		if(current == root) return true;
		else return false;
	}
	
	public boolean atLeaf() {
		//returns true if current references a leaf other wise returns false
		if(current != null && current.data != (char) 128) {
			return true;
		}
		else return false;
	 }
	
	public char current() {
		//returns the data value in the node referenced by current
		return current.data;
	}
	
	//return array of paths to leaves. Using 1's and 0's
	public String[] pathsToLeaves() {
		/*returns an array of 128 strings (some of which could be null) with all paths from the root to the leaves
		Each string will be a string of 0s and 1s. Store the path for a particular leaf in the array
		at the location of the leaf value’s character code
		*/
		String[] path = new String[128];
		String leafPath = "";
		path = pathsToLeaves(path, leafPath, root);
		return path;
	}
	
	private String[] pathsToLeaves(String[] path, String leafPath, Node n) {
		current = n;
		
		if(atLeaf()) {
			path[(int) n.data] = leafPath;
			return path;
		}
		
		//move left
		leafPath = (leafPath + "0");
		pathsToLeaves(path, leafPath, n.left);
		
		//move right but remove the left call
		leafPath = leafPath.substring(0, (leafPath.length() - 1));
		leafPath = (leafPath + "1");
		pathsToLeaves(path, leafPath, n.right);
		
		return path;
	}
	
	public String toString() {
		//returns a string representation of the tree using the postorder format
		// discussed in class
		return toString(root);

	}
	
	//returns string representation of the tree
	private String toString(Node r) {		
		current = r;
		
		if(atLeaf()) {
			return ("" + r.data);
		}
		return toString(r.left) + toString(r.right) + ("" + r.data);
	}
}
