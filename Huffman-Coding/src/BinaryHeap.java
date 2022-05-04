public class BinaryHeap {
 //implements a binary heap where the heap rule is the value in the parent
 //node is less than
 //or equal to the values in the child nodes

 //the implementation uses parallel arrays to store the priorities and the
 // trees
 //you must use this implementation

	int priority[];
	HuffmanTree trees[];
	int size;
	
	//creates BinaryHeap with parallel arrays
	public BinaryHeap(int s) {
		priority = new int[s];
		trees = new HuffmanTree[s];
		size = 0;
	}
	
	//removes minimum value and shifts tree accordingly
	public void removeMin() {
		//PRE: size != 0
		 //removes the priority and tree at the root of the heap
		
		if(size == 0) return;
		
		//values of priority and tree that are in the right most node of the deepest level.
		//value to be moved
		int mInt = priority[size];
		HuffmanTree mTree = trees[size];
		
		priority[size] = 0;
		trees[size] = null;
		
		int i = 1;
		
		//checks if in bound and not at leaf
		while(i * 2 <= size && priority[2 * i] != 0) {
			
			//checks if current position has 2 children then compares values in children
			//if values are equal, right value gets moved up
			if(priority[(2 * i) + 1] != 0) {
				//left value smaller
				if(priority[2 * i] < priority[(2 * i) + 1]) {
					priority[i] = priority[2 * i];
					trees[i] = trees[2 * i];
					i = (2 * i);
					
				//right value smaller
				} else {
					priority[i] = priority[(2 * i) + 1];
					trees[i] = trees[(2 * i) + 1];
					i = (2 * i + 1);
				}
				
			//only one child, compare to priority thats being moved	
			} else {
				if(priority[2 * i] < mInt) {
					priority[i] = priority[2 * i];
					trees[i] = trees[2 * i];
					
					i = 2 * i;
					priority[i] = mInt;
					trees[i] = mTree;
					size--;
					return;
				}else {
					priority[i] = mInt;
					trees[i] = mTree;
					size--;
					return;
				}
			}
		}
		
		priority[i] = mInt;
		trees[i] = mTree;
		size--;
	}

	public int getMinPriority() {
		//PRE: size != 0
		//return the priority in the root of the heap
		if(size == 0) return -1;
		
		return priority[1];
	}	

	public HuffmanTree getMinTree() {
		 //PRE: size != 0
		 //return the tree in the root of the heap
		if(size == 0) return null;
		
		return trees[1];
	}
	
	public boolean full() {
		 //return true if the heap is full otherwise return false
		if(priority.length == size) return true;
		else return false;
	}
	
	
	public void insert(int p, HuffmanTree t) {
		 //insert the priority p and the associated tree t into the heap
		//PRE !full()
		
		if(full() == true) return;
		
		//tree is empty, insert into spot 1
		if(size == 0) {
			priority[1] = p;
			trees[1] = t;
			size++;
			return;
		}
		
		int position = size + 1;
		
		//walk up path checking if parent is larger than inserting value
		while((priority[position / 2]) > p){
				priority[position] = priority[position / 2];
				trees[position] = trees[position / 2];
				position = position / 2;
		}
		
		priority[position] = p;
		trees[position] = t;
		size++;
	}
	
	public int getSize() {
		 //return the number of values, (priority , tree) pairs, in the heap
		return size;
	}
}