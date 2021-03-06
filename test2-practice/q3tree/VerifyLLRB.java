package q3tree;

/**
 * VerifyLLRB
 * 
 * Placeholder class for the static method verifyLLRB() which
 * takes a tree of red-black nodes and verifies that the tree
 * satisfies the constraints of a left-leaning red-black tree.
 * Do not assume that any red-black properties hold; the point of
 * the method is to check whether they hold.
 * 
 * CSCI 345
 * Test 2 Practice Problem 3.
 */
public class VerifyLLRB {

    /**
     * Determine whether a given tree made up of
     * RBNodes satisfies the left-learning red-black tree
     * properties (except that it does not check that
     * the absolute root is black). The blackHeight fields
     * of the nodes are not assumed to be correct before this
     * method is called, but, as a side affect, this method
     * sets the blackHeight field of every node in the tree
     * rooted at the given node to the correct blackHeight
     * value. Null references are valid (trivial) left-leaning
     * red-black trees.
     * @param root The root of the (sub)tree being verified
     * @return true if the tree rooted at root is a left-leaning
     * red-black tree, false otherwise.
     * POSTCONDITION: The blackHeight of every node in the
     * tree is set to the right value if the tree verifies as
     * a left-leaning red-black tree
     */
	public static boolean verifyLLRB(RBNode root) {
		if(root == null) return true;

		RBNode right = root.right;
		RBNode left = root.left;

		if(!verifyLLRB(root.left) || !verifyLLRB(root.right)) return false;
		
		if(right != null && right.isRed) return false;

		if(left != null && left.isRed){
			if(left.left != null && left.left.isRed)	
				return false;
		}

		int rbh = right == null ? 0 : right.blackHeight;
		int lbh = left == null ? 0 : left.blackHeight;
		
		if(lbh != rbh) return false;
		else root.blackHeight = root.isRed ? rbh : rbh + 1;

		return true;
	}
}
