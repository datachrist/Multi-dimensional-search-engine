package multidimensionalsearch;



import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;


public class RedBlackBST {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private RedBlackNode root;     // root of the BST

    // BST helper node data type
    private class RedBlackNode {
     //   private Long key;           // key
        private Node val;         // associated data
        private RedBlackNode left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int N;             // subtree count

        public RedBlackNode( Node val, boolean color, int N) {
          //  this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
        }
    }

   /*************************************************************************
    *  Node helper methods
    *************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(RedBlackNode x) {
        if (x == null) return false;
        return (x.color == RED);
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(RedBlackNode x) {
        if (x == null) return 0;
        return x.N;
    } 


   /*************************************************************************
    *  Size methods
    *************************************************************************/

    // return number of key-value pairs in this symbol table
    public int size() { return size(root); }

    // is this symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

   /*************************************************************************
    *  Standard BST search
    *************************************************************************/

    // value associated with the given key; null if no such key
    public Node get(Node hi) { return get(root, hi); }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Node get(RedBlackNode x, Node hi) {
        while (x != null) {
            int cmp = hi.compareToPrice(x.val);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    // is there a key-value pair with the given key?
    public boolean contains(Node hi) {
        return (get(hi) != null);
    }

    
    // is there a key-value pair with the given key in the subtree rooted at x?
//    private boolean contains(Node x, Double key) {
//        return (get(x, key) != null);
//     }

   /*************************************************************************
    *  Red-black insertion
    *************************************************************************/

    // insert the key-value pair; overwrite the old value with the new value
    // if the key is already present
    public void put(Node val) {
        root = put(root, val);
        root.color = BLACK;
        // assert check();
        
    }

    // insert the key-value pair in the subtree rooted at h
    private RedBlackNode put(RedBlackNode h,Node val) { 
        if (h == null) return new RedBlackNode( val, RED, 1);

        int cmp = val.compareToPrice(h.val);
        
        if (cmp < 0 ) 
        	h.left  = put(h.left, val); 
        else if (cmp > 0 ) 
        	h.right = put(h.right, val); 
        else {    
        /*	if(val.getName().isEmpty()){
        		ArrayList<Long> tempName=h.val.getName();
        		val.setName(tempName);
        	}
        	Node tempNode = new Node();
        	tempNode= val;
        	tempNode.flag = true;
        	h.val = tempNode;
        		return h;
        	delete(h.val);
        	h.val=val;*/
        }

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;

        return h;
    }

   /*************************************************************************
    *  Red-black deletion
    *************************************************************************/

    // delete the key-value pair with the minimum key
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

//    // delete the key-value pair with the minimum key rooted at h
    private RedBlackNode deleteMin(RedBlackNode h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


  
    // delete the key-value pair with the given key
    public int delete(Node key) { 
        if (!contains(key)) {
           // System.err.println("symbol table does not contain " + key);
            return 0;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
        return 1;
    }

    // delete the key-value pair with the given key rooted at h
    private RedBlackNode delete(RedBlackNode h, Node key) { 
        // assert contains(h, key);

        if (key.compareToPrice(h.val) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareToPrice(h.val) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareToPrice(h.val) == 0) {
                RedBlackNode x = min(h.right);
                h.val.setPrice(x.val.getPrice());
               // h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

   /*************************************************************************
    *  red-black tree helper functions
    *************************************************************************/

    // make a left-leaning link lean to the right
    private RedBlackNode rotateRight(RedBlackNode h) {
        // assert (h != null) && isRed(h.left);
        RedBlackNode x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private RedBlackNode rotateLeft(RedBlackNode h) {
        // assert (h != null) && isRed(h.right);
        RedBlackNode x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(RedBlackNode h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //     || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        //if((h.left!=null))        
        h.left.color = !h.left.color;
       // if(h.right!=null)
        h.right.color = !h.right.color;
        
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private RedBlackNode moveRedLeft(RedBlackNode h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private RedBlackNode moveRedRight(RedBlackNode h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private RedBlackNode balance(RedBlackNode h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }


    // the smallest key; null if no such key
    public Node min() {
        if (isEmpty()) return null;
        return min(root).val;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private RedBlackNode min(RedBlackNode x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    // the largest key; null if no such key
    public Node max() {
        if (isEmpty()) return null;
        return max(root).val;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private RedBlackNode max(RedBlackNode x) { 
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 


    /**
     * 	this method is used to find the number of items 
     * whose name contains n, and their prices fall 
     * within the given range, [low, high].
     * */
    public int FindPriceRange(Long n,Double low,Double high){
    	RedBlackNode t = root;
    	
    	if( t == null )
             return 0;
         Stack<RedBlackNode> s = new Stack<RedBlackNode>();
         int count=0;
 		while (true) {

 			if (t != null) {
 				s.push(t);
 				t = t.left;
 			} else {
 				if (!s.isEmpty()) {
 					t = s.pop();
 					
 					if ((t.val.getPrice()>=low)&&(t.val.getPrice()<=high)&&(t.val.getName().contains(n)))
 						count++;
 					t = t.right;
 				} else {
 					break;
 				}
 			}
 		}        	
		
		return count;
	}
    /**
     * Internal method to find the smallest item with containing n in name in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
  
    public Double findMinContainN(Long name) {
	RedBlackNode r = root;
	Stack<RedBlackNode> s = new Stack<RedBlackNode>();
	while (true) {

		if (r != null) {
			s.push(r);
			r = r.left;
		} else {
			if (!s.isEmpty()) {
				r = s.pop();
				
				if (r.val.getName().contains(name))
					return r.val.getPrice();
				r = r.right;
			} else {
				break;
			}
		}
	}
	return 0.0;
}       
    
    /**
     * Internal method to find the smallest item with containing n in name in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    public Double findMaxContainN(Long name)
    {        	
    	RedBlackNode r = root;
    Stack<RedBlackNode> s = new Stack<RedBlackNode>();
	while (true) {

		if (r != null) {
			s.push(r);
			r = r.right;
		} else {
			if (!s.isEmpty()) {
				r = s.pop();
				if (r.val.getName().contains(name))
					return r.val.getPrice();
				r = r.left;
			} else {
				break;
			}
		}
	}
	return 0.0;
    }
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }
    private void printTree( RedBlackNode t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.val.price );
            printTree( t.right );
        }
    }


 
}

